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
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.NewsViewModel
import org.koin.android.ext.android.inject

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news),
    SwipeRefreshLayout.OnRefreshListener {
    companion object {
        private val TAG = "NewsFragment"
    }

    private val newsAdapter: NewsAdapter by inject()
    private val viewModel: NewsViewModel by inject()

    private val onItemClickListener = View.OnClickListener {
        val viewHolder = it.tag as RecyclerView.ViewHolder
        val position = viewHolder.adapterPosition
        val bundle = Bundle()
//        if (list[position].isPresent) {
//            list[position].get().let { item ->
//                bundle.putString("newsTitle", item.title)
//                bundle.putString("newsLink", item.link)
//            }
//        }
        it.findNavController().navigate(R.id.action_newsFragment_to_newsDetailFragment, bundle)
    }
    private lateinit var list: List<Single<NewsItem>>
    override fun initView() {
        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.newsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        }

        with(viewModel) {
            newsItemsLiveData.observe(viewLifecycleOwner, Observer {
                newsAdapter.setNewsItem(it)
            })
        }
    }

    override fun onRefresh() {
        viewModel.reload()
        binding.swipeRefreshLayout.isRefreshing = false
    }
//    interface DataProvider {
//        val newsItems : LiveData<List<Optional<NewsItem>>>
//    }
//    interface ActionHandler {
//        fun refresh()
//    }
}