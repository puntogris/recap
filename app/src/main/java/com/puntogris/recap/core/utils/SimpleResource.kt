package com.puntogris.recap.core.utils

import com.puntogris.recap.R

sealed class SimpleResource {
    object Success : SimpleResource()
    class Error(val error: Int = R.string.general_error_message) : SimpleResource()

    companion object Factory {
        inline fun build(function: () -> Unit): SimpleResource =
            try {
                function()
                Success
            } catch (e: Exception) {
                println(e.localizedMessage)
                Error()
            }
    }
}