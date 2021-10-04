package com.puntogris.recap.data.repo.recap

import androidx.paging.PagingData
import com.puntogris.recap.model.Recap
import com.puntogris.recap.model.RecapInteractions
import com.puntogris.recap.utils.*
import kotlinx.coroutines.flow.Flow

interface IRecapRepository {
    suspend fun saveRecapIntoDb(recap: Recap): SimpleResult
    fun getRecapsPagingData(order: RecapOrder): Flow<PagingData<Recap>>
    fun getReviewsPagingData(order: ReviewOrder): Flow<PagingData<Recap>>
    fun getDraftsPagingData(): Flow<PagingData<Recap>>
    suspend fun getRecapWithId(recapId: String): Result<Recap>
    suspend fun getUserRecapInteractions(recapId: String): RecapInteractions?
    suspend fun deleteDraft(recap: Recap):SimpleResult
    suspend fun saveRecapLocalDb(recap: Recap): SimpleResult
}