package kr.ac.jejunu.myrealtrip.ui.cate.adapter.holder

import android.view.View
import kr.ac.jejunu.myrealtrip.base.BaseViewHolder
import kr.ac.jejunu.myrealtrip.databinding.NewsItemBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.cate.adapter.itemviewmodel.NewsItemViewModel

class NewsHolder(val binding: NewsItemBinding) : BaseViewHolder<NewsItem?>(binding.root) {

    private val viewModel = NewsItemViewModel()
    override fun bind(item: NewsItem?) {
        viewModel.bind(item)
        binding.itemViewModel = viewModel
    }

    fun setListener(clickListener: View.OnClickListener) {
        itemView.setOnClickListener(clickListener)
    }

}