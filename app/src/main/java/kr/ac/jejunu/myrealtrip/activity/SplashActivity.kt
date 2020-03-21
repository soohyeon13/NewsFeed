package kr.ac.jejunu.myrealtrip.activity

import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val SPLACH_TIME : Long = 1300
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val centerLogoWidth = width/3
        val otherLogoWidth = centerLogoWidth/3
        binding.centerContainer.apply {
            minimumWidth = centerLogoWidth
            minimumHeight = centerLogoWidth
        }
        centerLogoLoad(centerLogoWidth)
        logoLoad(otherLogoWidth,R.drawable.ic_wifi_signal,binding.leftLogo)
        logoLoad(otherLogoWidth,R.drawable.ic_worldwide,binding.rightLogo)
    }
    private fun centerLogoLoad(width: Int) {
        Glide.with(this)
            .load(R.drawable.ic_news)
            .override(width-80,width-80)
            .into(binding.centerLogo)
    }
    private fun logoLoad(width: Int, drawable: Int, view: ImageView) {
        Glide.with(this)
            .load(drawable)
            .override(width,width)
            .into(view)
    }
}