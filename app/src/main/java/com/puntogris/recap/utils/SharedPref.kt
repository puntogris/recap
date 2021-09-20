package com.puntogris.recap.utils

import android.content.Context
import androidx.annotation.Keep
import androidx.preference.PreferenceManager
import com.puntogris.recap.utils.Constants.PREF_APP_THEME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Keep
class SharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getAppTheme() = sharedPreferences.getString(PREF_APP_THEME, ThemeUtils.SYSTEM)
}