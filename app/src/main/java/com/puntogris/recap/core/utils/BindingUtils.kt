package com.puntogris.recap.core.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Timestamp
import com.puntogris.recap.R
import com.puntogris.recap.feature_recap.domain.model.Recap

@BindingAdapter("imageWithGlide")
fun ImageView.setImageWithGlide(image: String?) {
    val uri = if (image.isNullOrBlank()) R.drawable.ic_desert else image
    Glide
        .with(context)
        .load(uri)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade(Constants.CROSS_FADE_DURATION))
        .into(this)
}

@BindingAdapter("showRecapBodyMenu")
fun FloatingActionButton.setShowRecapBodyMenu(show: Boolean) {
    if (show) show() else hide()
}

@BindingAdapter("recapNumberCard")
fun TextView.setRecapNumberCard(recap: Recap) {
    var recapNumber = "S" + recap.season.toString().padStart(2, '0')

    if (recap.episode != 0) {
        recapNumber += "\nE" + recap.episode.toString().padStart(2, '0')
    }

    text = recapNumber
}

@BindingAdapter("monthYearFromTimestamp")
fun TextView.setMonthYearFromTimestamp(timestamp: Timestamp) {
    text = timestamp.toDate().getMonthYearFormatted()
}

fun TextView.setTimeToUnlockProfileEdit(seconds: Long) {
    val days = (seconds / 86400).toInt()
    val hours = ((seconds % 86400) / 3600).toInt()
    val minutes = (((seconds % 86400) % 3600) / 60).toInt()

    text = context.getString(R.string.time_to_unlock_profile_edit, days, hours, minutes)
}