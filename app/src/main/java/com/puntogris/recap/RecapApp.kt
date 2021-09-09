package com.puntogris.recap

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.puntogris.recap.utils.DataStore
import com.puntogris.recap.utils.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RecapApp: Application() {

    @Inject lateinit var dataStore: DataStore

    override fun onCreate() {
        super.onCreate()

        applyAppTheme()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

    }

    private fun applyAppTheme(){
        val theme = runBlocking { dataStore.appTheme() }
        ThemeUtils.applyTheme(theme)
    }
}