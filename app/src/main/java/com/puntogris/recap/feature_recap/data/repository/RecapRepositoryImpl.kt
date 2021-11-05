package com.puntogris.recap.feature_recap.data.repository

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.firestore.Query
import com.puntogris.recap.R
import com.puntogris.recap.Report
import com.puntogris.recap.core.utils.RecapOrder
import com.puntogris.recap.core.utils.Result
import com.puntogris.recap.core.utils.ReviewOrder
import com.puntogris.recap.core.utils.SimpleResult
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.data.remote.FirestoreRecapPagingSource
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapInteractions
import com.puntogris.recap.core.utils.Constants.ANDROID_FALL_BACK_URL
import com.puntogris.recap.core.utils.Constants.DEEP_LINK_PATH
import com.puntogris.recap.core.utils.Constants.DOMAIN_URI_PREFIX
import com.puntogris.recap.core.utils.Constants.INTERACTIONS_COLLECTION
import com.puntogris.recap.core.utils.Constants.RECAPS_COLLECTION
import com.puntogris.recap.core.utils.Constants.SQUARE_APP_LOGO_URL
import com.puntogris.recap.core.utils.Constants.USERS_COLLECTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RecapRepositoryImpl(
    private val firebase: FirebaseClients,
    private val recapDao: RecapDao,
    private val context: Context
) : RecapRepository {

    override suspend fun saveRecapIntoDb(recap: Recap): SimpleResult = withContext(Dispatchers.IO) {
        SimpleResult.build {
            val ref = firebase.firestore
                .collection(USERS_COLLECTION)
                .document(firebase.currentUid()!!)
                .collection(RECAPS_COLLECTION)
                .document()

            recap.id = ref.id
            recap.deepLink = generateRecapDynamicLink(recap)
            ref.set(recap).await()
        }
    }

    private suspend fun generateRecapDynamicLink(recap: Recap): String {
        return firebase.links.shortLinkAsync {
            link = Uri.parse(DEEP_LINK_PATH + recap.id)
            domainUriPrefix = DOMAIN_URI_PREFIX

            androidParameters(context.packageName) {
                fallbackUrl = Uri.parse(ANDROID_FALL_BACK_URL)
            }

            socialMetaTagParameters {
                title = recap.title
                description = context.getString(R.string.recap_link_title, recap.title)
                imageUrl = Uri.parse(SQUARE_APP_LOGO_URL)
            }

        }.await().shortLink?.path!!
    }

    override fun getRecapsPaged(order: RecapOrder): Flow<PagingData<Recap>> {
        val baseQuery = firebase
            .firestore
            .collection(RECAPS_COLLECTION)
        val query = when (order) {
            RecapOrder.LATEST -> baseQuery.orderBy("created", Query.Direction.DESCENDING)
            RecapOrder.OLDEST -> baseQuery.orderBy("created", Query.Direction.ASCENDING)
            RecapOrder.POPULAR -> baseQuery.orderBy("likes", Query.Direction.DESCENDING)
        }

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { FirestoreRecapPagingSource(query) }.flow
    }

    override fun getReviewsPaged(order: ReviewOrder): Flow<PagingData<Recap>> {
        val query = firebase
            .firestore
            .collection(RECAPS_COLLECTION)
            .whereEqualTo("aproved", false)

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { FirestoreRecapPagingSource(query) }.flow
    }

    override fun getDraftsPagingData(): Flow<PagingData<Recap>> {
        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { recapDao.getDraftsPaged() }.flow
    }

    override suspend fun getRecap(recapId: String): Result<Exception, Recap> =
        withContext(Dispatchers.IO) {
            Result.build {
                firebase
                    .firestore
                    .collection(RECAPS_COLLECTION)
                    .document(recapId)
                    .get()
                    .await()
                    .toObject(Recap::class.java)!!

            }
        }

    override suspend fun getUserRecapInteractions(recapId: String): RecapInteractions? =
        withContext(Dispatchers.IO) {
            firebase
                .firestore
                .collection(USERS_COLLECTION)
                .document(firebase.currentUid()!!)
                .collection(INTERACTIONS_COLLECTION)
                .document(recapId)
                .get()
                .await()
                .toObject(RecapInteractions::class.java)

        }

    override suspend fun deleteDraft(recap: Recap): SimpleResult {
        return if (recapDao.delete(recap.id) != 0) SimpleResult.Success else SimpleResult.Failure
    }

    override suspend fun saveRecapLocalDb(recap: Recap) = withContext(Dispatchers.IO) {
        recap.id = firebase.firestore.collection("recaps").document().id
        if (recapDao.insert(recap) != 0L) SimpleResult.Success else SimpleResult.Failure
    }

    override suspend fun reportRecap(report: Report): SimpleResult =
        withContext(Dispatchers.IO) {
            SimpleResult.build {
                report.uid = firebase.currentUid()!!
                firebase.firestore.collection("reports").add(report)
            }
        }
}