package kr.ac.jejunu.myrealtrip.ui.cate.adapter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import kr.ac.jejunu.myrealtrip.ui.news.NewsFragment
import kr.ac.jejunu.myrealtrip.util.Cate

class CatePageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    val bundle = Bundle()
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> NewsFragment.newInstance(Cate.MAINNEWS.query)
            1 -> NewsFragment.newInstance(Cate.BUSINESS.query)
            2 -> NewsFragment.newInstance(Cate.ENTERTAINMENT.query)
            3 -> NewsFragment.newInstance(Cate.GENERAL.query)
            4 -> NewsFragment.newInstance(Cate.HEALTH.query)
            5 -> NewsFragment.newInstance(Cate.SCIENCE.query)
            6 -> NewsFragment.newInstance(Cate.SPORTS.query)
            else -> NewsFragment.newInstance(Cate.TECHNOLOGY.query)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> Cate.MAINNEWS.cate
            1 -> Cate.BUSINESS.cate
            2 -> Cate.ENTERTAINMENT.cate
            3 -> Cate.GENERAL.cate
            4 -> Cate.HEALTH.cate
            5 -> Cate.SCIENCE.cate
            6 -> Cate.SPORTS.cate
            else -> Cate.TECHNOLOGY.cate
        }
    }

    override fun getCount(): Int = 7
}