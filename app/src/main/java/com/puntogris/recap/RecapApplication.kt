package com.puntogris.recap

import android.app.Application
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.lyft.kronos.KronosClock
import com.puntogris.recap.core.data.local.SharedPreferences
import com.puntogris.recap.core.utils.ThemeUtils
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RecapApplication : Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var kronosClock: KronosClock

    override fun onCreate() {
        super.onCreate()

        kronosClock.syncInBackground()

        ThemeUtils.applyTheme(sharedPreferences.getAppTheme())

        initializeAppChecker()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeAppChecker() {
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
            //SafetyNetAppCheckProviderFactory.getInstance()
        )
    }

}