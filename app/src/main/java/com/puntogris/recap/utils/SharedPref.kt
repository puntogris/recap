package com.puntogris.recap.utils

import android.content.Context
import androidx.annotation.Keep
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Keep
class SharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun appTheme() = sharedPreferences.getString(PREF_APP_THEME, ThemeUtils.SYSTEM)

    fun setAppThemePreference(value: String) = sharedPreferences.edit().putString(PREF_APP_THEME, value)

    companion object {
        private const val APP_PREFERENCES_NAME = "app_preferences"
        const val PREF_LAST_VERSION_CODE = "last_version_code"
        const val PREF_APP_THEME = "pref_app_theme"
        const val PREF_SHOW_NOTIFICATIONS = "pref_show_notifications"
    }
}