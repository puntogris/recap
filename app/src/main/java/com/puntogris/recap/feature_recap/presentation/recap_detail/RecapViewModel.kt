package com.puntogris.recap.feature_recap.presentation.recap_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.puntogris.recap.core.domain.use_case.GetCurrentAuthUser
import com.puntogris.recap.core.domain.use_case.isLoggedIn
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.use_case.GetRecapInteractionsUseCase
import com.puntogris.recap.feature_recap.domain.use_case.GetRecapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RecapViewModel @Inject constructor(
    private val getRecap: GetRecapUseCase,
    getRecapInteractions: GetRecapInteractionsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val getCurrentAuthUser: GetCurrentAuthUser
) : ViewModel() {

    private fun recapIdFromArgs() =
        savedStateHandle.get<Recap>("recap")?.id ?: savedStateHandle.get<String>("recapId")!!

    val recapInteractions = getRecapInteractions(recapIdFromArgs()).asLiveData()

    private val _recap = MutableStateFlow(Recap())
    val recap = _recap.asStateFlow()

    val recapState = liveData {
        val recapIdArgs = savedStateHandle.get<String>("recapId")
        if (recapIdArgs != null) {
            emit(getRecap(recapIdArgs))
        } else {
            emit(Resource.Success(savedStateHandle.get<Recap>("recap")!!))
        }
    }

    fun updateRecap(recap: Recap) {
        _recap.value = recap
    }

    fun isUserLoggedIn() = getCurrentAuthUser.isLoggedIn()

}