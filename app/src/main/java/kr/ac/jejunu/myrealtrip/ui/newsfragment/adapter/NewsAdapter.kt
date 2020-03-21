package kr.ac.jejunu.myrealtrip.ui.newsfragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.NewsItemBinding
import kr.ac.jejunu.myrealtrip.model.data.Item
import kr.ac.jejunu.myrealtrip.model.data.State
import kr.ac.jejunu.myrealtrip.model.data.datamodel.htmlmodel.HtmlDataModel
import kr.ac.jejunu.myrealtrip.ui.newsviewmodel.itemviewmodel.NewsItemViewModel

class NewsAdapter(private val dataModel: HtmlDataModel) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private lateinit var binding: NewsItemBinding
//    private var state = State.LOADING
    private lateinit var newsList: List<Item>

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = NewsItemViewModel(dataModel)
        fun bind(news: Item) {
            viewModel.bind(news)
            binding.itemViewModel = viewModel
        }
    }

//    companion object {
//        val RssDiffCallback = object : DiffUtil.ItemCallback<Item>() {
//            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
//                Log.d("NewsAdapter","oldItem : $oldItem newItem: $newItem")
//                return ((oldItem.title == newItem.title) && (oldItem.link == newItem.link))
//            }
//
//            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
//                Log.d("NewsAdapter","oldItem : $oldItem newItem: $newItem")
//                return oldItem == newItem
//            }
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        Log.d("test","${super.getItemCount()}")
//        return super.getItemCount()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
//        binding = DataBindingUtil.inflate(
//            LayoutInflater.from(parent.context),
//            R.layout.news_item,
//            parent,
//            false
//        )
//        return NewsViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
//        getItem(position)?.let { holder.bind(it) }
//    }
//
//    fun setState(state: State) {
//        this.state = state
//        notifyItemChanged(super.getItemCount())
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.news_item,
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    fun setNewsItem(news: List<Item>) {
        this.newsList = news
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        if (::newsList.isInitialized) newsList.size else 0

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }
}