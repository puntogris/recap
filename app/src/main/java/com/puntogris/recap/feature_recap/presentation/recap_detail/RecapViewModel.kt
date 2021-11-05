package com.puntogris.recap.feature_recap.presentation.recap_detail

import androidx.lifecycle.*
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.use_case.GetRecapInteractionsUseCase
import com.puntogris.recap.feature_recap.domain.use_case.GetRecapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecapViewModel @Inject constructor(
    private val getRecap: GetRecapUseCase,
    private val getRecapInteractions: GetRecapInteractionsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private fun recapIdFromArgs() =
        savedStateHandle.get<Recap>("recap")?.id ?: savedStateHandle.get<String>("recapId")!!

    val recapInteractions = getRecapInteractions(recapIdFromArgs()).asLiveData()

    private val _recap = MutableLiveData<Recap>()
    val recap: LiveData<Recap> = _recap

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

    fun isUserLoggedIn() = true

}