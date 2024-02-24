package com.abhay.moviedb.util

import android.view.View
import android.widget.ImageView
import com.abhay.moviedb.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadImagesWithGlideExt(url: String?) {
    if (url != null) {
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.placeholder_movies)
            .error(R.drawable.no_image_available_placeholder)
            .into(this)
    }
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}