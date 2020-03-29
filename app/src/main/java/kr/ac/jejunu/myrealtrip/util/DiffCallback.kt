package kr.ac.jejunu.myrealtrip.util

import androidx.recyclerview.widget.DiffUtil
import kr.ac.jejunu.myrealtrip.domain.model.NewsItem

class DiffCallback(
    private val oldList: List<NewsItem>,
    private val newList: List<NewsItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].link == newList[newItemPosition].link
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val isTitle = oldList[oldItemPosition].title == newList[newItemPosition].title
        val isDesc = oldList[oldItemPosition].desc == newList[newItemPosition].desc
        val isImgUrl = oldList[oldItemPosition].imageUrl == newList[newItemPosition].imageUrl
        val isContent = oldList[oldItemPosition].content == newList[newItemPosition].content
        val isKeyWord = oldList[oldItemPosition].keyWord == newList[newItemPosition].keyWord
        return isTitle && isDesc && isImgUrl && isContent && isKeyWord
    }
}