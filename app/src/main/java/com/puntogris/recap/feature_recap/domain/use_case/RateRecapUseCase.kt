package com.puntogris.recap.feature_recap.domain.use_case

import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository

class RateRecapUseCase(
    private val repository: RecapRepository
){
    suspend operator fun invoke(recapId: String, score: Int): SimpleResource{
        return repository.rateRecap(recapId, score)
    }
}