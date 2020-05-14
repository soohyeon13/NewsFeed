package kr.ac.jejunu.myrealtrip.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentNewsBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.news.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {
    companion object {
        private val TAG = "NewsFragment"
    }

    private val refresh = SwipeRefreshLayout.OnRefreshListener {
        viewModel.clear("news")
        viewModel.reload()
        binding.swipeRefreshLayout.isRefreshing = false
    }
    private val newsAdapter: NewsAdapter by inject()
    private val viewModel: NewsViewModel by inject()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun observe() {
        if (viewModel.searchItemLiveData.value.isNullOrEmpty()) {
            loadInitNewItem()
        } else {
            loadSearchResult()
        }
    }

    private fun loadInitNewItem() {
        viewModel.newsItemsLiveData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it.toString())
            newsAdapter.setNewsItem(it)
        })
    }

    private fun loadSearchResult() {
        viewModel.searchItemLiveData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, it.toString())
            newsAdapter.setNewsItem(it)
        })
    }

    private fun initView() {
        binding.swipeRefreshLayout.setOnRefreshListener(refresh)
        mLayoutManager = LinearLayoutManager(requireContext())
        binding.newsRecycler.apply {
            layoutManager = mLayoutManager
            adapter = newsAdapter
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy <= 0) return
                    val totalItemCount = mLayoutManager.itemCount
                    val lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
                    val visibleThreshold = 3
                    if (totalItemCount <= lastVisibleItem + visibleThreshold) {
                        viewModel.loadPage()
                    }
                }
            })
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        newsAdapter.setOnItemClickListener(object : OnItemClickEvent<NewsItem> {
            override fun onItemClick(item: NewsItem?) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item?.link))
                startActivity(intent)
//                val bundle = Bundle()
//                bundle.apply {
//                    this.putString("newsTitle", item?.title)
//                    this.putString("newsLink", item?.link)
//                    this.putStringArrayList("newsKeyWords", item?.keyWord as ArrayList<String>)
//                }
//                findNavController().navigate(R.id.action_newsFragment_to_newsDetailFragment, bundle)
            }
        })
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.search(it) }
                viewModel.clear("search")
                loadSearchResult()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        binding.searchView.setOnCloseListener {
            viewModel.clear("search")
            loadInitNewItem()
            false
        }
    }
}