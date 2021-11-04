package com.puntogris.recap.core.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.puntogris.recap.R

@BindingAdapter("imageWithGlide")
fun ImageView.setImageWithGlide(image: String?){
    val uri = if (image.isNullOrBlank()) R.drawable.ic_desert else image

    Glide
        .with(context)
        .load(uri)
        .centerCrop()
        .into(this)
}

@BindingAdapter("showRecapBodyMenu")
fun FloatingActionButton.setShowRecapBodyMenu(show: Boolean){
    if (show) show() else hide()
}