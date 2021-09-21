package com.puntogris.recap.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PublicProfile(
    val uid: String = "",
    val name: String = "",
    val bio: String = "",
    val registered: Timestamp = Timestamp.now(),
    val likesCounter: Int = 0,
    val recapsCounter: Int = 0,
    val profileImageUrl: String = ""
):Parcelable