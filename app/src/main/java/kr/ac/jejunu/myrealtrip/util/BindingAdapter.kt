package kr.ac.jejunu.myrealtrip.util

import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import kr.ac.jejunu.myrealtrip.R

@BindingAdapter("ImageUrl")
fun setImageUrl(view:ImageView,url:String?) {
    Glide.with(view.context)
        .load(url)
        .override(200,200)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}