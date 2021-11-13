package com.puntogris.recap.feature_profile.presentation.util

import com.puntogris.recap.R

sealed class EditProfileResult {
    object Success : EditProfileResult()
    class EditLimit(val secondsToUnlock: Long) : EditProfileResult()
    class Error(val error: Int = R.string.general_error_message) : EditProfileResult()
}
