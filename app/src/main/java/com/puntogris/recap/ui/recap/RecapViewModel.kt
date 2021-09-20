package com.puntogris.recap.ui.recap

import androidx.lifecycle.*
import com.puntogris.recap.data.repo.recap.RecapRepository
import com.puntogris.recap.models.Recap
import com.puntogris.recap.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecapViewModel @Inject constructor(
    private val recapRepository: RecapRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private fun recapIdFromArgs() =
        savedStateHandle.get<Recap>("recap")?.id ?: savedStateHandle.get<String>("recapId")!!

    val recapInteractions = liveData {
        emit(recapRepository.getUserRecapInteractions(recapIdFromArgs()))
    }

    private val _recap = MutableLiveData<Recap>()
    val recap: LiveData<Recap> = _recap

    val recapState = liveData {
        val recapIdArgs = savedStateHandle.get<String>("recapId")
        if (recapIdArgs != null){
            emit(recapRepository.getRecapWithId(savedStateHandle.get<String>("recapId")!!))
        }else{
            emit(Result.Success(savedStateHandle.get<Recap>("recap")!!))
        }
    }

    fun updateRecap(recap: Recap){
        _recap.value = recap
    }

    fun isUserLoggedIn() = true

}