package com.puntogris.recap.feature_recap.domain.model

import androidx.annotation.Keep

@Keep
class RecapInteractions(
    val liked: Boolean,
    val favourited: Boolean
)