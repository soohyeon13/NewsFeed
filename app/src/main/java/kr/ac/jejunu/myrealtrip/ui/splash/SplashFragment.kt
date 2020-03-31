package kr.ac.jejunu.myrealtrip.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import androidx.databinding.library.BuildConfig
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.activity.NewsActivity
import kr.ac.jejunu.myrealtrip.base.BaseFragment
import kr.ac.jejunu.myrealtrip.databinding.FragmentSplashBinding
import kr.ac.jejunu.myrealtrip.ui.splash.viewmodel.SplashViewModel
import org.koin.android.ext.android.inject

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    private val SPLACH_TIME: Long = 1300
    private val viewModel : SplashViewModel by inject()
    init{
        viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView() {
        binding.versionText.text = "v${BuildConfig.VERSION_NAME}"
        val dm = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val centerLogoWidth = width / 3
        val otherLogoWidth = centerLogoWidth / 3
        val centerImg = (centerLogoWidth / 2) * 1.41
        binding.centerContainer.apply {
            layoutParams.width = centerLogoWidth
            layoutParams.height = centerLogoWidth
        }
        centerLogoLoad(centerImg.toInt())
        logoLoad(otherLogoWidth, R.drawable.ic_wifi_signal, binding.leftLogo)
        logoLoad(otherLogoWidth, R.drawable.ic_worldwide, binding.rightLogo)


        Handler().postDelayed({
            val action = SplashFragmentDirections.actionSplashFragmentToNewsFragment()
            findNavController().navigate(action)
        }, SPLACH_TIME)
    }
    private fun centerLogoLoad(width: Int) {
        Glide.with(this)
            .load(R.drawable.ic_news)
            .override(width, width)
            .into(binding.centerLogo)
    }

    private fun logoLoad(width: Int, drawable: Int, view: ImageView) {
        Glide.with(this)
            .load(drawable)
            .override(width, width)
            .into(view)
    }
}