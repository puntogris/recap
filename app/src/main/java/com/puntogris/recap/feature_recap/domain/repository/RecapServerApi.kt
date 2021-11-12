package com.puntogris.recap.feature_recap.domain.repository

import androidx.paging.PagingSource
import com.puntogris.recap.feature_recap.data.data_source.local.RecapEntity
import com.puntogris.recap.feature_recap.domain.model.*

interface RecapServerApi {

    fun getRecapsPagingSource(order: RecapOrder): PagingSource<*, Recap>

    fun getReviewsPagingSource(order: ReviewOrder): Pair<PagingSource<*, Recap>, String?>

    suspend fun getRecap(recapId: String): Recap

    suspend fun publishRecap(recap: RecapEntity): String

    suspend fun reportRecap(report: Report)

    suspend fun getRecapInteractionsWithCurrentUser(recapId: String): RecapInteractions?

    suspend fun rateRecap(recapId: String, score: Int)
}