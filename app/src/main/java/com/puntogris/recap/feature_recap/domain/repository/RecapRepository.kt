package com.puntogris.recap.feature_recap.domain.repository

import androidx.paging.PagingData
import com.puntogris.recap.Report
import com.puntogris.recap.core.utils.RecapOrder
import com.puntogris.recap.core.utils.Result
import com.puntogris.recap.core.utils.ReviewOrder
import com.puntogris.recap.core.utils.SimpleResult
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapInteractions
import kotlinx.coroutines.flow.Flow

interface RecapRepository {

    fun getRecapsPaged(order: RecapOrder): Flow<PagingData<Recap>>

    fun getReviewsPaged(order: ReviewOrder): Flow<PagingData<Recap>>

    fun getDraftsPagingData(): Flow<PagingData<Recap>>

    suspend fun saveRecapIntoDb(recap: Recap): SimpleResult

    suspend fun getRecap(recapId: String): Result<Exception, Recap>

    suspend fun getUserRecapInteractions(recapId: String): RecapInteractions?

    suspend fun deleteDraft(recap: Recap): SimpleResult

    suspend fun saveRecapLocalDb(recap: Recap): SimpleResult

    suspend fun reportRecap(report: Report): SimpleResult
}