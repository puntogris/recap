package com.puntogris.recap.utils

import androidx.appcompat.app.AppCompatDelegate
import com.puntogris.recap.R

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

    fun getThemeNameRes(theme: String?): Int{
        return when (theme) {
            LIGHT -> R.string.theme_light
            DARK -> R.string.theme_dark
            SYSTEM -> R.string.theme_system
            else -> R.string.error
        }
    }
}