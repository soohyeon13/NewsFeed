package kr.ac.jejunu.myrealtrip.ui.newsdetail

import android.os.Bundle
import android.view.View
import com.mrt.nasca.NascaViewListener
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>(R.layout.fragment_news_detail) {
    companion object {
        private val TAG = "NewsDetailFragment"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {
        arguments?.let {
            binding.detailWebView.loadUrl("${it.get("newsLink")}")
            binding.keyword = it.getStringArrayList("newsKeyWords")
        }
        binding.detailWebView.listener = object : NascaViewListener() {
            override fun onImageClicked(index: Int, url: String) {
                super.onImageClicked(index, url)
                binding.failedLoad.visibility = View.GONE
            }

            override fun onLoadingFailed(url: String, errorCode: Int, errorMsg: CharSequence?) {
                super.onLoadingFailed(url, errorCode, errorMsg)
                binding.detailWebView.visibility = View.INVISIBLE
                binding.failedLoad.visibility = View.VISIBLE
            }

            override fun onProgressChanged(progress: Int) {
                super.onProgressChanged(progress)
                binding.progress = progress
            }
        }
    }
}