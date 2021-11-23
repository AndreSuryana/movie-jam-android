package com.example.moviejam.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.constant.Constants

object Extensions {

    fun ImageView.loadImage(path: String?) {
        val url = Constants.IMAGE_BASE_URL + path
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
            .error(R.drawable.placeholder)
            .into(this)
    }
}