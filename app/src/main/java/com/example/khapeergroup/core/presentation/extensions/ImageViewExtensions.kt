package com.example.khapeergroup.core.presentation.extensions

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.khapeergroup.R

fun ImageView.loadImage(
    context: Context,
    url: String?,
    defaultImg: Int = R.drawable.ic_launcher_background
) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(context).load(url).placeholder(circularProgressDrawable)
        .error(defaultImg).into(this)
}
