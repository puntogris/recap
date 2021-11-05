package com.puntogris.recap.feature_recap.domain.use_case

import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository

class GetRecapUseCase(
    private val repository: RecapRepository
) {
    suspend operator fun invoke(recapId: String): Resource<Recap> {
        return repository.getRecap(recapId)
    }
}