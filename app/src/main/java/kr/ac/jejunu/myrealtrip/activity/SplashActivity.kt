package kr.ac.jejunu.myrealtrip.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import kr.ac.jejunu.myrealtrip.MainActivity
import kr.ac.jejunu.myrealtrip.R
import kr.ac.jejunu.myrealtrip.databinding.ActivitySplashBinding
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val SPLACH_TIME: Long = 1300
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
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
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, SPLACH_TIME)
    }

    override fun onBackPressed() {
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