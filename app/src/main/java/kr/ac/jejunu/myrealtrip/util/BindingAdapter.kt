package kr.ac.jejunu.myrealtrip.util

import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.mrt.nasca.NascaView
import kr.ac.jejunu.myrealtrip.R
object BindingAdapter  {
    @JvmStatic
    @BindingAdapter("ImageUrl")
    fun setImageUrl(view:NascaView,url:String?) {
        url?.let { view.loadImages(it) }
    }
    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view:View,visible : Boolean) {
        view.visibility  = if (visible) View.VISIBLE else View.GONE
    }
}
