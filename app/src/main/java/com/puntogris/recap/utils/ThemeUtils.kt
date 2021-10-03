package com.puntogris.recap.utils

import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {

    const val LIGHT = "light"
    const val DARK = "dark"
    const val SYSTEM = "system"

    fun applyTheme(theme: String?) {
        when (theme) {
            LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}