package com.puntogris.recap.feature_recap.domain.use_case

import androidx.paging.PagingData
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapOrder
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import kotlinx.coroutines.flow.Flow

class GetRecapsUseCase(
    private val repository: RecapRepository
) {
    operator fun invoke(order: RecapOrder): Flow<PagingData<Recap>> {
        return repository.getRecapsPaged(order)
    }
}