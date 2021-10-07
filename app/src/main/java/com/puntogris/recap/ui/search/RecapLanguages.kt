package com.puntogris.recap.ui.search

import androidx.annotation.StringRes
import com.puntogris.recap.R

enum class RecapLanguages(@StringRes val nameRes: Int, val code: String){
    ENGLISH(R.string.english, "EN"),
    SPANISH(R.string.spanish, "ES"),
    PORTUGUESE(R.string.portuguese, "PT")
}
