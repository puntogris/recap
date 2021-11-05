package com.puntogris.recap.feature_recap.domain.use_case

import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import kotlinx.coroutines.flow.flow

class GetRecapInteractionsUseCase(
    private val repository: RecapRepository
) {
    operator fun invoke(recapId: String) = flow {
        emit(repository.getUserRecapInteractions(recapId))
    }
}