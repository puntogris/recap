package com.puntogris.recap.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageWithGlide")
fun ImageView.setImageWithGlide(image: String?){
    if (image != null){
        Glide
            .with(context)
            .load(image)
            .centerCrop()
            .into(this)
    }
}