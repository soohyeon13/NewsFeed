package kr.ac.jejunu.myrealtrip.ui.cate.tab.technology

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.TabFragmentTechnologyBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.cate.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.cate.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.ui.cate.tab.business.BusinessFragment
import org.koin.android.ext.android.inject
import java.util.*

class TechnologyFragment : BaseFragment<TabFragmentTechnologyBinding>(R.layout.tab_fragment_technology) {
    companion object{
        fun newInstance(cate: String): Fragment {
            val fragment = TechnologyFragment()
            val args = Bundle()
            args.putString("cate", cate)
            fragment.arguments = args
            return fragment
        }
    }
    private val viewModel : TechnologyViewModel by inject()
    private val newsAdapter: NewsAdapter by inject()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun initView() {
        arguments?.getString("cate")?.let {
            viewModel.loadCateNews(it)
        }
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
        viewModel.technologyNewsItemsLiveData.observe(viewLifecycleOwner,Observer {
            newsAdapter.setNewsItem(it)
        })
    }
}