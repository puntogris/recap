package com.puntogris.recap.feature_recap.domain.use_case

import androidx.paging.PagingData
import com.puntogris.recap.core.utils.ReviewOrder
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import kotlinx.coroutines.flow.Flow

class GetReviewsUseCase(
    private val repository: RecapRepository
) {
    operator fun invoke(order: ReviewOrder): Flow<PagingData<Recap>> {
        return repository.getReviewsPaged(order)
    }
}