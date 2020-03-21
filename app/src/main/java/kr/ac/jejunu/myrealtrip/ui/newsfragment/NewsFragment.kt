package kr.ac.jejunu.myrealtrip.ui.newsfragment

import android.util.DisplayMetrics
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kr.ac.jejunu.myrealtrip.BR
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentNewsBinding
import kr.ac.jejunu.myrealtrip.model.data.State
import kr.ac.jejunu.myrealtrip.ui.newsfragment.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.newsviewmodel.NewsViewModel
import org.koin.android.ext.android.inject
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(R.layout.fragment_news),SwipeRefreshLayout.OnRefreshListener {
    private val newsAdapter : NewsAdapter by inject()
//    private lateinit var newsAdapter : NewsAdapter
    override val viewModel: NewsViewModel by inject()
//    override fun getViewModel(): Class<NewsViewModel> = NewsViewModel by inject()
//    override fun getBindingVariable(): Int = BR.viewModel

    override fun initView() {
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.newsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL))
        }
//        viewModel.rssLiveData.observe(this, Observer {
//            newsAdapter.submitList(it)
//        })
//        viewModel.getState().observe(this, Observer { state ->
//            if (!viewModel.listIsEmpty()) {
//                newsAdapter.setState(state ?: State.DONE)
//            }
//        })
//        if (!viewModel.listIsEmpty()) {
//            newsAdapter.setState(state ?: State.DONE)
//        }
        with(viewModel) {
            getNews()
            rssLiveData.observe(this@NewsFragment, Observer {
                it.channel?.item?.let { it1 -> newsAdapter.setNewsItem(it1) }
            })
        }
    }

    override fun onRefresh() {
        with(viewModel) {
            getNews()
            rssLiveData.observe(this@NewsFragment, Observer {
                it.channel?.item?.let { it1 -> newsAdapter.setNewsItem(it1) }
            })
        }
        binding.swipeRefreshLayout.isRefreshing = false
    }

}