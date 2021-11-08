package com.puntogris.recap.feature_recap.presentation.rate_recap

import androidx.lifecycle.ViewModel
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.use_case.RateRecapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RateRecapViewModel @Inject constructor(
    private val rateRecapUseCase: RateRecapUseCase
) : ViewModel() {

    suspend fun sendRating(recapId: String, score: Int): SimpleResource {
        return rateRecapUseCase(recapId, score)
    }
}