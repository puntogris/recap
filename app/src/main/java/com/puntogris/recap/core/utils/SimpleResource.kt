package com.puntogris.recap.core.utils


sealed class SimpleResource {
    object Success : SimpleResource()
    class Error(val uiText: UiText = UiText.unknownError()) : SimpleResource()

    companion object Factory {
        inline fun build(function: () -> Unit): SimpleResource =
            try {
                function()
                Success
            } catch (e: Exception) {
                Error()
            }
    }
}