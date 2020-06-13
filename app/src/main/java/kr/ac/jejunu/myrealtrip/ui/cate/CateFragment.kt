package kr.ac.jejunu.myrealtrip.ui.cate

import android.os.Bundle
import android.view.View
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentCateBinding
import kr.ac.jejunu.myrealtrip.ui.cate.adapter.CatePageAdapter

class CateFragment : BaseFragment<FragmentCateBinding>(R.layout.fragment_cate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {
         val adapter = CatePageAdapter(childFragmentManager)
        binding.cateViewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.cateViewPager)
    }
}