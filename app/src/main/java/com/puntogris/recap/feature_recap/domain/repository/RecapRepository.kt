package com.puntogris.recap.feature_recap.domain.repository

import androidx.paging.PagingData
import com.puntogris.recap.core.utils.RecapOrder
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.ReviewOrder
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapInteractions
import com.puntogris.recap.feature_recap.domain.model.Report
import kotlinx.coroutines.flow.Flow

interface RecapRepository {

    fun getRecapsPaged(order: RecapOrder): Flow<PagingData<Recap>>

    fun getReviewsPaged(order: ReviewOrder): Flow<PagingData<Recap>>

    fun getDraftsPaged(): Flow<PagingData<Recap>>

    suspend fun saveRecap(recap: Recap): SimpleResource

    suspend fun getRecap(recapId: String): Resource<Recap>

    suspend fun reportRecap(report: Report): SimpleResource

    suspend fun getUserRecapInteractions(recapId: String): RecapInteractions?

    suspend fun deleteDraft(recap: Recap): SimpleResource

    suspend fun saveRecapDraft(recap: Recap): SimpleResource
}