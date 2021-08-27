package com.puntogris.recap

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecapApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}