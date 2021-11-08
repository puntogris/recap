package com.puntogris.recap

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.AppCheckProviderFactory
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
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
        initializeAppChecker()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeAppChecker(){
        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
            DebugAppCheckProviderFactory.getInstance()
            //SafetyNetAppCheckProviderFactory.getInstance()
        )
    }

    private fun applyAppTheme() {
        ThemeUtils.applyTheme(sharedPreferences.getAppTheme())
    }
}