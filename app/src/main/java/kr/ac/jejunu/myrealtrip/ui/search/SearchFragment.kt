package kr.ac.jejunu.myrealtrip.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.activity.NewsMainViewModel
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentSearchBinding
import kr.ac.jejunu.myrealtrip.ui.cate.adapter.NewsAdapter
import kr.ac.jejunu.myrealtrip.ui.search.viewmodel.SearchViewModel
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel : SearchViewModel by inject()
    private val newsAdapter : NewsAdapter by inject()
    private val mainViewModel : NewsMainViewModel by navGraphViewModels(R.id.main_navi)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }
    private fun initView() {
        binding.searchList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            setHasFixedSize(false)
        }
    }

    private fun observe() {
        with(mainViewModel) {
            this.searchWord.observe(viewLifecycleOwner, Observer {
                viewModel.loadSearchResult(it)
            })
        }
        with(viewModel) {
            this.searchItemsLiveData.observe(viewLifecycleOwner, Observer {
                newsAdapter.setNewsItem(it)
            })
        }
    }
}