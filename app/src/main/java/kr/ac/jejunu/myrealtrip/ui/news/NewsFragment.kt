package kr.ac.jejunu.myrealtrip.ui.news

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentNewsBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.news.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnLoadListener
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnLoadScroll
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news),
    SwipeRefreshLayout.OnRefreshListener,
    OnItemClickEvent<NewsItem> {
    companion object {
        private val TAG = "NewsFragment"
    }
    private lateinit var scrollListener : OnLoadScroll
    private val newsAdapter: NewsAdapter by inject()
    private val viewModel: NewsViewModel by inject()
    private lateinit var mLayoutManager : LinearLayoutManager
    override fun initView() {
        binding.executePendingBindings()
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        mLayoutManager = LinearLayoutManager(requireContext())
        binding.newsRecycler.apply {
            layoutManager = mLayoutManager
            adapter = newsAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

        newsAdapter.setOnItemClickListener(this)
        with(viewModel) {
            newsItemsLiveData.observe(viewLifecycleOwner, Observer {
                Log.d(TAG,"$it")
                newsAdapter.setNewsItem(it)
            })
        }
        setScrollListener()
    }

    private fun setScrollListener() {
        scrollListener = OnLoadScroll(mLayoutManager)
        scrollListener.setLoadListener(object : OnLoadListener{
            override fun onLoad() {
//                newsAdapter.addLoadingView()
                viewModel.loadPage()
//                newsAdapter.removeLoadingView()
//                scrollListener.setLoad()
            }
        })
        binding.newsRecycler.addOnScrollListener(scrollListener)
    }

    override fun onRefresh() {
        viewModel.reload()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun onItemClick(item: NewsItem?) {
        val bundle = Bundle()
        bundle.putString("newsTitle",item?.title)
        bundle.putString("newsLink",item?.link)
        findNavController().navigate(R.id.action_newsFragment_to_newsDetailFragment,bundle)
    }
}