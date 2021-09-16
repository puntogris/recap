package com.puntogris.recap.data.repo.recap

import androidx.paging.PagingData
import com.puntogris.recap.models.Recap
import com.puntogris.recap.models.Report
import com.puntogris.recap.utils.RecapOrder
import com.puntogris.recap.utils.ReviewOrder
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.flow.Flow

interface IRecapRepository {
    suspend fun saveRecapIntoDb(recap: Recap): SimpleResult
    fun getRecapsPagingData(order: RecapOrder): Flow<PagingData<Recap>>
    fun getReviewsPagingData(order: ReviewOrder): Flow<PagingData<Recap>>
    suspend fun sendRecapReport(report: Report): SimpleResult
}