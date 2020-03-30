package kr.ac.jejunu.myrealtrip.ui.news.listener

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnLoadScroll : RecyclerView.OnScrollListener {
    private lateinit var mOnLoadListener: OnLoadListener
    private var visibleThreshold = 3
    private var isLoad = false
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    private var mLayoutManager: RecyclerView.LayoutManager

    fun setLoad() {
        this.isLoad = false
    }

    fun getLoaded(): Boolean {
        return isLoad
    }

    fun setLoadListener(mOnLoadListener: OnLoadListener) {
        this.mOnLoadListener = mOnLoadListener
    }
    constructor(layoutManager:LinearLayoutManager) {
        this.mLayoutManager = layoutManager
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
}