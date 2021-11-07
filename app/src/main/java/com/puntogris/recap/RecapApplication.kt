package com.puntogris.recap

import android.app.Application
import com.puntogris.recap.core.data.local.SharedPreferences
import com.puntogris.recap.core.utils.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RecapApplication : Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        applyAppTheme()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }

    private fun applyAppTheme() {
        ThemeUtils.applyTheme(sharedPreferences.getAppTheme())
    }
}