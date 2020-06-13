package kr.ac.jejunu.myrealtrip.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_news.*
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.ActivityNewsBinding
import kr.ac.jejunu.myrealtrip.ui.news.NewsFragment

class NewsActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding : ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        navController = Navigation.findNavController(this, R.id.fragment)
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
                    navigationIcon = null
                }
            }
        }
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

