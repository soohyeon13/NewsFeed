package kr.ac.jejunu.myrealtrip.ui.news.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.ItemLoadingBinding
import kr.ac.jejunu.myrealtrip.databinding.NewsItemBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.news.adapter.holder.LoadHolder
import kr.ac.jejunu.myrealtrip.ui.news.adapter.holder.NewsHolder
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnLoadListener
import kr.ac.jejunu.myrealtrip.ui.news.viewmodel.itemviewmodel.NewsItemViewModel
import kr.ac.jejunu.myrealtrip.util.DiffCallback

class NewsAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private val TAG = "NewsAdapter"
        private val VIEW_TYPE_NEWS = 0
        private val VIEW_TYPE_LOADING = 1
    }

    private lateinit var mOnLoadListener: OnLoadListener
    private var newsList = mutableListOf<NewsItem>()
    private lateinit var mOnItemClickListener: OnItemClickEvent<NewsItem>

    fun setOnItemClickListener(itemClickListener: OnItemClickEvent<NewsItem>) {
        mOnItemClickListener = itemClickListener
    }

    fun setOnLoadListener(onLoadListener: OnLoadListener) {
        this.mOnLoadListener = onLoadListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            VIEW_TYPE_NEWS -> {
                val binding: NewsItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.news_item,
                    parent,
                    false
                )
                return NewsHolder(binding)
            }
            else -> {
                val binding: ItemLoadingBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_loading,
                    parent,
                    false
                )
                return LoadHolder(binding)
            }
        }
    }

    fun setNewsItem(news: List<NewsItem>) {
        val diffCallback = DiffCallback(this.newsList, news)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.newsList.clear()
        this.newsList.addAll(news)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = newsList.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsHolder) holder.bind(newsList[position],mOnItemClickListener)
        else (holder as LoadHolder).binding.itemLoading.isIndeterminate =true
    }
}