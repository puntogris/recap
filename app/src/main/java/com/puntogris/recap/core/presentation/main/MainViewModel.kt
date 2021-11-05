package com.puntogris.recap.core.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.puntogris.recap.BuildConfig
import com.puntogris.recap.core.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sharedPref: SharedPref
) : ViewModel() {

    val appVersionStatus = liveData {
        if (sharedPref.lastVersionCode() < BuildConfig.VERSION_CODE) {
            sharedPref.updateLastVersionCode()
            emit(true)
        }
    }
}