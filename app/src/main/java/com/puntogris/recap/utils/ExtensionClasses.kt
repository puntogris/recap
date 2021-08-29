package com.puntogris.recap.utils

sealed class SimpleResult{
    object Success: SimpleResult()
    object Failure: SimpleResult()
}

sealed class LoginResult {
    object InProgress: LoginResult()
    class Success(): LoginResult()
    class Error(): LoginResult()
}