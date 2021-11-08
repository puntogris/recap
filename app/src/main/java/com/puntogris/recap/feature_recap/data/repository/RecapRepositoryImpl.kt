package com.puntogris.recap.feature_recap.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.utils.IDGenerator
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.data.data_source.toEntity
import com.puntogris.recap.feature_recap.domain.model.*
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import com.puntogris.recap.feature_recap.domain.repository.RecapServerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RecapRepositoryImpl(
    private val recapDao: RecapDao,
    private val recapServerApi: RecapServerApi
) : RecapRepository {

    override suspend fun publishRecap(recap: Recap): Resource<RecapLink> =
        withContext(Dispatchers.IO) {
            Resource.build {
                val recapLink = recapServerApi.publishRecap(recap.toEntity())
                recapDao.delete(recap.id)
                RecapLink(recapLink)
            }
        }

    override fun getRecapsPaged(order: RecapOrder): Flow<PagingData<Recap>> {
        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { recapServerApi.getRecapsPagingSource(order) }.flow
    }

    override fun getReviewsPaged(order: ReviewOrder): Flow<PagingData<Recap>> {
        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { recapServerApi.getReviewsPagingSource(order) }.flow
    }

    override suspend fun getRecap(recapId: String): Resource<Recap> = withContext(Dispatchers.IO) {
        Resource.build {
            recapServerApi.getRecap(recapId)
        }
    }

    override suspend fun getUserRecapInteractions(recapId: String): RecapInteractions? =
        withContext(Dispatchers.IO) {
            recapServerApi.getRecapInteractionsWithCurrentUser(recapId)
        }

    override suspend fun deleteDraft(recap: Recap): SimpleResource = withContext(Dispatchers.IO) {
        SimpleResource.build {
            recapDao.delete(recap.id)
        }
    }

    override suspend fun saveRecapDraft(recap: Recap) = withContext(Dispatchers.IO) {
        SimpleResource.build {
            recap.toEntity().apply {
                id = IDGenerator.randomID()
                recapDao.insert(this)
            }
        }
    }

    override suspend fun rateRecap(): SimpleResource = withContext(Dispatchers.IO) {
        SimpleResource.build {
            recapServerApi.rateRecap("t3HlQH05PGs6JzNJPmlI", 1)
        }
    }

    override suspend fun reportRecap(report: Report): SimpleResource = withContext(Dispatchers.IO) {
        SimpleResource.build {
            recapServerApi.reportRecap(report)
        }
    }
}