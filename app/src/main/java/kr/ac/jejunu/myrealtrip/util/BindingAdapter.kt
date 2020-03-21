package kr.ac.jejunu.myrealtrip.util

import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kr.ac.jejunu.myrealtrip.R

@BindingAdapter("ImageUrl")
fun setImageUrl(view:ImageView,url:String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_worldwide)
        .into(view)
}