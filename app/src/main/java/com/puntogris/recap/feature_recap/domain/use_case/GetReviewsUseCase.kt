package com.puntogris.recap.feature_recap.domain.use_case

import androidx.paging.PagingData
import androidx.paging.filter
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.ReviewOrder
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetReviewsUseCase(
    private val repository: RecapRepository
) {
    operator fun invoke(order: ReviewOrder): Flow<PagingData<Recap>> {
        return repository.getReviewsPaged(order)
    }
}