package com.puntogris.recap.feature_auth.presentation.util

sealed class LoginResult {
    object InProgress : LoginResult()
    object Success : LoginResult()
    object Error : LoginResult()
}