package com.puntogris.recap.ui.main

import androidx.lifecycle.*
import com.puntogris.recap.BuildConfig
import com.puntogris.recap.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sharedPref: SharedPref
):ViewModel() {

    val appVersionStatus = liveData {
        if (sharedPref.lastVersionCode() < BuildConfig.VERSION_CODE) {
            sharedPref.updateLastVersionCode()
            emit(true)
        }
    }
}