package kr.ac.jejunu.myrealtrip.ui.news.listener

import java.util.*

interface OnItemClickEvent<T> {
    fun onItemClick(item:T)
}