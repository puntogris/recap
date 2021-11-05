package com.puntogris.recap.core.utils

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val uiText: UiText = UiText.unknownError()) : Resource<T>()

    companion object Factory {
        inline fun <T> build(function: () -> T): Resource<T> =
            try {
                Success(function.invoke())
            } catch (e: Exception) {
                Error()
            }
    }
}


