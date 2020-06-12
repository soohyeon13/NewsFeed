package kr.ac.jejunu.myrealtrip.ui.splash.viewmodel

import android.util.Log
import kr.ac.jejunu.myrealtrip.base.BaseViewModel
import kr.ac.jejunu.myrealtrip.domain.repository.Repository
import java.util.*

class SplashViewModel(private val repository: Repository) : BaseViewModel() {
    companion object {
        private val TAG = "SplashViewModel"
    }

    init {
        repository.loadNews().subscribe({}, {
            Log.d(TAG, "${it.printStackTrace()}")
        }).let { addDisposable(it) }
    }
}