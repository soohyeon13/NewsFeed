package kr.ac.jejunu.myrealtrip.ui.cate.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.NewsItemBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.cate.adapter.holder.NewsHolder
import kr.ac.jejunu.myrealtrip.ui.cate.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.util.DiffCallback

class NewsAdapter :
    RecyclerView.Adapter<NewsHolder>() {
    companion object {
        const val TAG = "NewsAdapter"
    }

    private var newsList = mutableListOf<NewsItem?>()
    private lateinit var mOnItemClickListener: OnItemClickEvent<NewsItem>

    fun setOnItemClickListener(itemClickListener: OnItemClickEvent<NewsItem>) {
        mOnItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding: NewsItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.news_item,
            parent,
            false
        )
        return NewsHolder(binding)
    }

    fun setNewsItem(news: List<NewsItem>) {
        val diffCallback = DiffCallback(this.newsList, news)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.newsList.clear()
        this.newsList.addAll(news)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = newsList.size ?: 0

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        Log.d(TAG,newsList[position].toString())
        holder.bind(newsList[position])
        holder.setListener(
            View.OnClickListener { mOnItemClickListener.onItemClick(newsList[position]) }
        )
    }
}