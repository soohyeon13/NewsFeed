package kr.ac.jejunu.myrealtrip.ui.cate.tab.health

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.TabFragmentHealthBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.cate.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.cate.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.ui.cate.tab.business.BusinessFragment
import org.koin.android.ext.android.inject

class HealthFragment :BaseFragment<TabFragmentHealthBinding>(R.layout.tab_fragment_health){
    companion object {
        fun newInstance(cate: String): Fragment {
            val fragment = HealthFragment()
            val args = Bundle()
            args.putString("cate", cate)
            fragment.arguments = args
            return fragment
        }
    }
    private val viewModel : HealthViewModel by inject()
    private val newsAdapter: NewsAdapter by inject()
    private lateinit var mLayoutManager: LinearLayoutManager
    private val refresh = SwipeRefreshLayout.OnRefreshListener {
        viewModel.reload()
        binding.swipeRefreshLayout.isRefreshing = false
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        viewModel.loadCateNews()
        binding.swipeRefreshLayout.setOnRefreshListener(refresh)
        mLayoutManager = LinearLayoutManager(requireContext())
        binding.newsRecycler.apply {
            layoutManager = mLayoutManager
            adapter = newsAdapter
            setHasFixedSize(true)
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    if (dy <= 0) return
//                    val totalItemCount = mLayoutManager.itemCount
//                    val lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()
//                    val visibleThreshold = 3
//                    if (totalItemCount <= lastVisibleItem + visibleThreshold) {
//                        viewModel.loadPage()
//                    }
//                }
//            })
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }
        newsAdapter.setOnItemClickListener(object : OnItemClickEvent<NewsItem> {
            override fun onItemClick(item: NewsItem?) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item?.link))
                startActivity(intent)
            }
        })
    }

    private fun observe() {
        viewModel.healthNewsItemsLiveData.observe(viewLifecycleOwner, Observer {
            newsAdapter.setNewsItem(it)
        })
    }
}