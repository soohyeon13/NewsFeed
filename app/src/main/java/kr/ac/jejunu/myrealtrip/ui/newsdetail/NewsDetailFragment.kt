package kr.ac.jejunu.myrealtrip.ui.newsdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
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
    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        binding.detailWebView.apply {
            arguments?.let {
                this.loadUrl("${it.get("newsLink")}")
                binding.keyword = it.getStringArrayList("newsKeyWords")
            }
            this.settings.javaScriptEnabled = true
            this.webChromeClient = WebChromeClient()
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