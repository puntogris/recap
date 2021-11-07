package com.puntogris.recap.feature_recap.domain.model

import androidx.annotation.StringRes
import com.puntogris.recap.R

enum class RecapOrder(@StringRes val titleRes: Int, val value: String) {
    LATEST(R.string.order_latest, "latest"),
    OLDEST(R.string.order_oldest, "oldest"),
    POPULAR(R.string.order_popular, "popular")
}