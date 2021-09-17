package com.puntogris.recap.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recap(
    var id : String = "",
    var title: String = "",
    var rating: Int = 0,
    var season: Int = 0,
    var type: String = "",
    var author: String = "",
    var html: String = "",
    var approved: Boolean = false,
    var category: String = "",
    var image: String = "",
    var deepLink: String = "",
    var created: Timestamp = Timestamp.now()
): Parcelable