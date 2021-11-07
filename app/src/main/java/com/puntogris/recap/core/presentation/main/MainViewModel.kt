package com.puntogris.recap.core.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.puntogris.recap.BuildConfig
import com.puntogris.recap.core.data.local.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sharedPreferences: SharedPreferences
) : ViewModel() {

    val appVersionStatus = liveData {
        if (sharedPreferences.lastVersionCode() < BuildConfig.VERSION_CODE) {
            sharedPreferences.updateLastVersionCode()
            emit(true)
        }
    }
}