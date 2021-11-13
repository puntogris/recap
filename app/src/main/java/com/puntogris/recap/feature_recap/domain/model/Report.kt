package com.puntogris.recap.feature_recap.domain.model

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.google.firebase.Timestamp
import com.puntogris.recap.R

@Keep
class Report(
    var uid: String = "",
    val recapId: String,
    val reason: String,
    val timestamp: Timestamp = Timestamp.now()
)