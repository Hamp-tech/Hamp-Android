package com.hamp.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImg(image: Any?) {
    if (image is String && image.isNotBlank()) Picasso.with(context).load(image).into(this)
    else if (image is Int) Picasso.with(context).load(image).into(this)
}