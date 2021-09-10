package com.puntogris.recap

import android.app.Application
import com.puntogris.recap.utils.SharedPref
import com.puntogris.recap.utils.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RecapApp: Application() {

    @Inject lateinit var sharedPref: SharedPref

    override fun onCreate() {
        super.onCreate()

        applyAppTheme()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }

    private fun applyAppTheme(){
        val theme = runBlocking { sharedPref.appTheme() }
        ThemeUtils.applyTheme(theme)
    }
}