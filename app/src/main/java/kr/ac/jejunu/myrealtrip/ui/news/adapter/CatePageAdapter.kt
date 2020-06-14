package kr.ac.jejunu.myrealtrip.ui.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.ac.jejunu.myrealtrip.ui.cate.tab.business.BusinessFragment
import kr.ac.jejunu.myrealtrip.ui.cate.tab.entertainment.EntertainFragment
import kr.ac.jejunu.myrealtrip.ui.cate.tab.general.GeneralFragment
import kr.ac.jejunu.myrealtrip.ui.cate.tab.health.HealthFragment
import kr.ac.jejunu.myrealtrip.ui.cate.tab.recent_news.RecentNewsFragment
import kr.ac.jejunu.myrealtrip.ui.cate.tab.science.ScienceFragment
import kr.ac.jejunu.myrealtrip.ui.cate.tab.sports.SportsFragment
import kr.ac.jejunu.myrealtrip.ui.cate.tab.technology.TechnologyFragment
import kr.ac.jejunu.myrealtrip.util.Cate

class CatePageAdapter(fm: Fragment) : FragmentStateAdapter(fm) {
    companion object{
        private val PAGE_CNT = 8
    }
    override fun getItemCount(): Int = PAGE_CNT

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> RecentNewsFragment.newInstance(Cate.MAINNEWS.query)
            1 -> BusinessFragment.newInstance(Cate.BUSINESS.query)
            2 -> EntertainFragment.newInstance(Cate.ENTERTAINMENT.query)
            3 -> GeneralFragment.newInstance(Cate.GENERAL.query)
            4 -> HealthFragment.newInstance(Cate.HEALTH.query)
            5 -> ScienceFragment.newInstance(Cate.SCIENCE.query)
            6 -> SportsFragment.newInstance(Cate.SPORTS.query)
            else -> TechnologyFragment.newInstance(Cate.TECHNOLOGY.query)
        }
    }
//    val bundle = Bundle()
//    override fun getItem(position: Int): Fragment {
//        return when (position) {
//            0 -> NewsFragment.newInstance(Cate.MAINNEWS.query)
//            1 -> NewsFragment.newInstance(Cate.BUSINESS.query)
//            2 -> NewsFragment.newInstance(Cate.ENTERTAINMENT.query)
//            3 -> NewsFragment.newInstance(Cate.GENERAL.query)
//            4 -> NewsFragment.newInstance(Cate.HEALTH.query)
//            5 -> NewsFragment.newInstance(Cate.SCIENCE.query)
//            6 -> NewsFragment.newInstance(Cate.SPORTS.query)
//            else -> NewsFragment.newInstance(Cate.TECHNOLOGY.query)
//        }
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return when (position) {
//            0 -> Cate.MAINNEWS.cate
//            1 -> Cate.BUSINESS.cate
//            2 -> Cate.ENTERTAINMENT.cate
//            3 -> Cate.GENERAL.cate
//            4 -> Cate.HEALTH.cate
//            5 -> Cate.SCIENCE.cate
//            6 -> Cate.SPORTS.cate
//            else -> Cate.TECHNOLOGY.cate
//        }
//    }

//    override fun getCount(): Int = 7
}