package com.puntogris.recap.model

import androidx.annotation.Keep
import com.google.firebase.Timestamp

@Keep
class Review(
    var id: String = "",
    val rating: Int = 0,
    val recapId: String = "",
    var reviewerId: String = "",
    val reason: String = "",
    val timestamp: Timestamp = Timestamp.now()
)