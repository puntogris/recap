package com.puntogris.recap.utils

import android.content.Context
import androidx.annotation.Keep
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.puntogris.recap.R
import com.puntogris.recap.utils.Constants.APP_PREFERENCES_NAME
import com.puntogris.recap.utils.Constants.APP_THEME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Keep
class DataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_PREFERENCES_NAME)

    suspend fun appTheme() = context.dataStore.data.first()[stringPreferencesKey(APP_THEME)] ?: ThemeUtils.SYSTEM

    fun appThemeFlow() = context.dataStore.data.map {
        val value = it[stringPreferencesKey(APP_THEME)] ?: ThemeUtils.SYSTEM
    }

    suspend fun setAppThemePreference(value: String) = context.dataStore.edit {
        it[stringPreferencesKey(APP_THEME)] = value
    }

}