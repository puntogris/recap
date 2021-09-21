package com.puntogris.recap.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puntogris.recap.BuildConfig
import com.puntogris.recap.utils.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPref: SharedPref
):ViewModel() {

    private val _appVersionStatus = MutableLiveData<Boolean>()
    val appVersionStatus: LiveData<Boolean> = _appVersionStatus

    init {
        viewModelScope.launch {
            if (sharedPref.lastVersionCode() < BuildConfig.VERSION_CODE) {
                sharedPref.updateLastVersionCode()
                _appVersionStatus.value = true
            }
        }
    }

}