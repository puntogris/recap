package com.puntogris.recap.core.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseRvViewModel : ViewModel() {

    private val _reselectedTabId = MutableLiveData<Int>()
    val reselectedTabId: LiveData<Int> = _reselectedTabId

    fun updateReselectedTabId(position: Int) {
        _reselectedTabId.value = position
    }
}