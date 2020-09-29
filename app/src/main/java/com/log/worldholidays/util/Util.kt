package com.log.worldholidays.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp
import com.log.worldholidays.R


fun ImageView.download_from_url(url: String?, progress_drawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(progress_drawable)
        .error(R.drawable.ic_undraw_page_not_found_su7k)


    GlideApp.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}





fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 48f
        start()
    }
}

@BindingAdapter("android:download_url")
fun glide_image_xml(view: ImageView, url: String?) {
    view.download_from_url(url, placeholderProgressBar(view.context))
}

fun glide_image_from_viewModel(view: ImageView, url: String?) {
    view.download_from_url(url, placeholderProgressBar(view.context))
}




