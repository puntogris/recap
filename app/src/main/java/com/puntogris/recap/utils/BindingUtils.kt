package com.puntogris.recap.utils

import android.widget.Button
import android.widget.ImageView
import android.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import jp.wasabeef.richeditor.RichEditor

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
