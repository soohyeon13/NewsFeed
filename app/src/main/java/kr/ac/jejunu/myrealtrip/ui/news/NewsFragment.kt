package kr.ac.jejunu.myrealtrip.ui.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news),
    SwipeRefreshLayout.OnRefreshListener,
    OnItemClickEvent<NewsItem> {
    companion object {
        private val TAG = "NewsFragment"
    }

    private val newsAdapter: NewsAdapter by inject()
    private val viewModel: NewsViewModel by inject()
    private var page = 0
    override fun initView() {
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.newsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        newsAdapter.setOnItemClickListener(this)
        with(viewModel) {
            newsItemsLiveData.observe(viewLifecycleOwner, Observer {
                page += 1
                viewModel.getPage(page)
                newsAdapter.setNewsItem(it)
            })
        }
    }

    override fun onRefresh() {
        viewModel.reload()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun onItemClick(item: NewsItem) {
        val bundle = Bundle()
        bundle.putString("newsTitle",item.title)
        bundle.putString("newsLink",item.link)
        findNavController().navigate(R.id.action_newsFragment_to_newsDetailFragment,bundle)
    }
}