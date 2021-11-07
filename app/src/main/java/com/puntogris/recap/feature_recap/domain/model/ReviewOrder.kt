package com.puntogris.recap.feature_recap.domain.model

import androidx.annotation.StringRes
import com.puntogris.recap.R

enum class ReviewOrder(@StringRes val titleRes: Int, val value: String) {
    ALL(R.string.order_all, "all"),
}