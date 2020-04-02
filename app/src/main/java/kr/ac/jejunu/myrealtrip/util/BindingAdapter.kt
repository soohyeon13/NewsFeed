package kr.ac.jejunu.myrealtrip.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("ImageUrl")
    fun setImageUrl(view: ImageView, url: String?) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .into(view)
    }
}
