package com.theapache64.twinkill.utils.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * To load image from URL
 */
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {

    val requestOption = RequestOptions()
        .centerCrop()

    Glide.with(imageView.context)
        .load(url)
        .apply(requestOption)
        .into(imageView)
}

/**
 * To load image using resource identifier integer when using data binding
 */
@BindingAdapter("android:src")
fun loadImageResourceId(imageView: ImageView, resourceId: Int) {
    imageView.setImageResource(resourceId)
}