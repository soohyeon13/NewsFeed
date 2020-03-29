package kr.ac.jejunu.myrealtrip.ui.news.adapter.holder

import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import kr.ac.jejunu.myrealtrip.base.BaseViewHolder
import kr.ac.jejunu.myrealtrip.databinding.ItemLoadingBinding
import kr.ac.jejunu.myrealtrip.databinding.NewsItemBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem

class LoadHolder(val binding:ItemLoadingBinding) : BaseViewHolder<NewsItem>(binding.root) {
    private lateinit var progress : ContentLoadingProgressBar
    override fun bind(item: NewsItem) {
        progress = binding.itemLoading
    }
}