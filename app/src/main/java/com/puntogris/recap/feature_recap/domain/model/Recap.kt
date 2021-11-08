package com.puntogris.recap.feature_recap.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Recap(
    var id: String = "",

    var title: String = "",

    var rating: Int = 0,

    var season: Int = 0,

    var episode: Int = 0,

    var type: String = "",

    var language: String = "",

    var author: String = "",

    var body: String = "",

    var status: String = RecapStatus.PENDING,

    var category: String = "",

    var image: String = "",

    var deepLink: String = "",

    var created: Timestamp = Timestamp.now(),

    val reviewers: List<String> = emptyList()

) : Parcelable

fun Recap.isPending() = status == RecapStatus.PENDING
fun Recap.isApproved() = status == RecapStatus.APPROVED
fun Recap.isRejected() = status == RecapStatus.REJECTED
