package kr.ac.jejunu.myrealtrip.ui.news.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.common.base.Optional
import io.reactivex.Single
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.NewsItemBinding
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem
import kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel.NewsItemViewModel

class NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val TAG = "NewsAdapter"

    private lateinit var binding: NewsItemBinding
    private lateinit var newsList: List<Single<NewsItem>>
    private lateinit var mOnItemClickListener: View.OnClickListener

    inner class NewsViewHolder(private val newsBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(newsBinding.root) {
        private val viewModel = NewsItemViewModel()
        fun bind(news: Single<NewsItem>) {
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

    fun setNewsItem(news: List<Single<NewsItem>>) {
        this.newsList = ArrayList(news)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        if (::newsList.isInitialized) newsList.size else 0

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }
}