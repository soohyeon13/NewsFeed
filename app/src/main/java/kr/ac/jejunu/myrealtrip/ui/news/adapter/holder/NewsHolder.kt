package kr.ac.jejunu.myrealtrip.ui.news.adapter.holder

import android.view.View
import android.widget.AdapterView
import kr.ac.jejunu.myrealtrip.base.BaseViewHolder
import kr.ac.jejunu.myrealtrip.databinding.NewsItemBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.itemviewmodel.NewsItemViewModel

class NewsHolder(val binding: NewsItemBinding) :BaseViewHolder<NewsItem>(binding.root) {
    private val viewModel = NewsItemViewModel()
    override fun bind(item: NewsItem, clickListener: OnItemClickEvent<NewsItem>) {
        viewModel.bind(item)
        binding.itemViewModel = viewModel
//        itemView.setOnClickListener(mOnItemClickListener)
        itemView.tag = this
    }
}