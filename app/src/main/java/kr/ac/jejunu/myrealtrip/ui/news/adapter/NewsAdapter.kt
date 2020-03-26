package kr.ac.jejunu.myrealtrip.ui.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.NewsItemBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.news.listener.OnItemClickEvent
import kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel.NewsItemViewModel
import java.util.*

class NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val TAG = "NewsAdapter"

    private lateinit var binding: NewsItemBinding
    private lateinit var newsList: List<Optional<NewsItem>>
    private lateinit var mOnItemClickListener: View.OnClickListener

    inner class NewsViewHolder(private val newsBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(newsBinding.root) {
        private val viewModel = NewsItemViewModel()
        fun bind(news: Optional<NewsItem>) {
            itemView.tag = this
            itemView.setOnClickListener(mOnItemClickListener)
            viewModel.bind(news)
            newsBinding.itemViewModel = viewModel
        }
    }

    fun setOnItemClickListener(itemClickListener: View.OnClickListener) {
        mOnItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.context),
        R.layout.news_item,
        parent,
        false
        )
        return NewsViewHolder(binding)
    }

    fun setNewsItem(news: List<Optional<NewsItem>>) {
        this.newsList = news
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        if (::newsList.isInitialized) newsList.size else 0

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }
}