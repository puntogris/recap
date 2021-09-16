package com.puntogris.recap.models

import androidx.annotation.StringRes
import com.puntogris.recap.R

class Report(
    var uid: String = "",
    val recapId: String,
    val reason: String
){
    enum class Reason(@StringRes val value: Int){
        INAPPROPRIATE(R.string.inappropriate),
        MISINFORMATION(R.string.misinformation)
    }

}