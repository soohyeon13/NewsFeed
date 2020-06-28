package kr.ac.jejunu.myrealtrip.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.ActivityNewsBinding
import kr.ac.jejunu.myrealtrip.ui.news.NewsFragment
import kr.ac.jejunu.myrealtrip.ui.news.NewsFragmentDirections
import kr.ac.jejunu.myrealtrip.ui.search.SearchFragmentArgs
import java.lang.IllegalArgumentException
import kotlin.math.log

class NewsActivity : AppCompatActivity(){
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding : ActivityNewsBinding
    private lateinit var mainViewModel : NewsMainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        navController = Navigation.findNavController(this, R.id.fragment)
        initViewModel()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashFragment) {
                hide()
            }
            if (destination.id == R.id.newsFragment) {
                show()
                binding.toolbar.apply {
                    inflateMenu(R.menu.menu)
                    search()
                    navigationIcon = null
                }
            }
            if (destination.id == R.id.searchFragment) {
                show()
                binding.toolbar.apply {
                    inflateMenu(R.menu.menu)
                    search()
                    navigationIcon = null
                }
            }
        }
    }
    private fun initViewModel() {
        try {
            val viewModelProvider = ViewModelProvider(
                navController.getViewModelStoreOwner(R.id.main_navi),
                ViewModelProvider.AndroidViewModelFactory(application)
            )
            mainViewModel = viewModelProvider.get(NewsMainViewModel::class.java)
        }catch (e : IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    private fun search() {
        val mSearchView : SearchView = binding.toolbar.menu.findItem(R.id.search).actionView as SearchView
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                navController.navigate(R.id.action_gloabal_searchFragment)
                query?.let { mainViewModel.searchWord.value = it }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun show() {
        binding.apply {
            bottomNavigationView.visibility = View.VISIBLE
            toolbar.visibility = View.VISIBLE
        }
    }
    private fun hide() {
        binding.apply {
            bottomNavigationView.visibility = View.GONE
            toolbar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}

