package kr.ac.jejunu.myrealtrip.activity

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class NewsMainViewModel(app : Application) : AndroidViewModel(app) {
    var searchWord = MutableLiveData<String>()
}