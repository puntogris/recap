package com.puntogris.recap.core.data.local

import android.content.Context
import androidx.annotation.Keep
import androidx.preference.PreferenceManager
import com.puntogris.recap.BuildConfig
import com.puntogris.recap.core.utils.Constants.LAST_VERSION_CODE
import com.puntogris.recap.core.utils.Constants.PREF_APP_THEME
import com.puntogris.recap.core.utils.ThemeUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Keep
class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getAppTheme() = sharedPreferences.getString(PREF_APP_THEME, ThemeUtils.SYSTEM)

    fun lastVersionCode() = sharedPreferences.getInt(LAST_VERSION_CODE, 0)

    fun updateLastVersionCode() {
        sharedPreferences.edit().putInt(LAST_VERSION_CODE, BuildConfig.VERSION_CODE).apply()
    }
}