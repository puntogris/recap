package com.puntogris.recap.feature_recap.domain.repository

import androidx.paging.PagingData
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.model.*
import kotlinx.coroutines.flow.Flow

interface RecapRepository {

    fun getRecapsPaged(order: RecapOrder): Flow<PagingData<Recap>>

    fun getReviewsPaged(order: ReviewOrder): Flow<PagingData<Recap>>

    suspend fun publishRecap(recap: Recap): Resource<RecapLink>

    suspend fun getRecap(recapId: String): Resource<Recap>

    suspend fun reportRecap(report: Report): SimpleResource

    suspend fun getUserRecapInteractions(recapId: String): RecapInteractions?

    suspend fun deleteDraft(recap: Recap): SimpleResource

    suspend fun saveRecapDraft(recap: Recap): SimpleResource

    suspend fun rateRecap(recapId: String, score: Int): SimpleResource
}