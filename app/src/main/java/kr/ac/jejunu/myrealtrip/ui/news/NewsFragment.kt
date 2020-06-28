package kr.ac.jejunu.myrealtrip.ui.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_news.*
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentNewsBinding
import kr.ac.jejunu.myrealtrip.ui.news.adapter.CatePageAdapter
import kr.ac.jejunu.myrealtrip.util.Cate

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {
    companion object {
        private val TAG = "NewsFragment"
        private val tabs = arrayOf(
            Cate.MAINNEWS,
            Cate.BUSINESS,
            Cate.ENTERTAINMENT,
            Cate.GENERAL,
            Cate.HEALTH,
            Cate.SCIENCE,
            Cate.SPORTS,
            Cate.TECHNOLOGY
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun observe() {
    }

    private fun initView() {
        tabClickEvent()
        binding.cateViewPager.apply {
            adapter = CatePageAdapter(this@NewsFragment)
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
        TabLayoutMediator(binding.tabLayout,binding.cateViewPager) { tab, position ->
            tab.text = tabs[position].cate
            tab.tag = tabs[position]
        }.attach()
    }

    private fun tabClickEvent() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
            override fun onTabSelected(p0: TabLayout.Tab?) {
                val cate = p0?.tag as Cate
                activity?.toolbar?.title = cate.cate
                println(cate.query)
            }
        })
    }
}