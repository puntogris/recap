package com.puntogris.recap.core.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.puntogris.recap.R
import com.puntogris.recap.feature_recap.domain.model.Recap

@BindingAdapter("imageWithGlide")
fun ImageView.setImageWithGlide(image: String?) {
    val uri = if (image.isNullOrBlank()) R.drawable.ic_desert else image
    println(image)
    Glide
        .with(context)
        .load(uri)
        .centerCrop()
        .into(this)
}

@BindingAdapter("showRecapBodyMenu")
fun FloatingActionButton.setShowRecapBodyMenu(show: Boolean) {
    if (show) show() else hide()
}

@BindingAdapter("recapNumberCard")
fun TextView.setRecapNumberCard(recap: Recap){
    var recapNumber = "S" + recap.season.toString().padStart(2,'0')

    if (recap.episode != 0){
        recapNumber += "\nE" + recap.episode.toString().padStart(2,'0')
    }

    text = recapNumber
}
