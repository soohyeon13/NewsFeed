package kr.ac.jejunu.myrealtrip.ui.news.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnLoadScroll : RecyclerView.OnScrollListener {
    private lateinit var mOnLoadListener: OnLoadListener
    private var visibleThreshold = 5
    private var isLoad = false
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    private val mLayoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager) {
        this.mLayoutManager = layoutManager
    }

    fun setLoad() {
        this.isLoad = false
    }

    fun getLoaded(): Boolean {
        return isLoad
    }

    fun setLoadListener(mOnLoadListener: OnLoadListener) {
        this.mOnLoadListener = mOnLoadListener
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return
        totalItemCount = mLayoutManager.itemCount
        lastVisibleItem = (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (!isLoad && totalItemCount <= lastVisibleItem+visibleThreshold) {
            mOnLoadListener.onLoad()
            isLoad = true
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPosition: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPosition.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPosition[i]
            }else if (lastVisibleItemPosition[i] > maxSize) {
                maxSize = lastVisibleItemPosition[i]
            }
        }
        return maxSize
    }
}