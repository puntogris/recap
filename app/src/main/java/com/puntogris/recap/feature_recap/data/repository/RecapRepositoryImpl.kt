package com.puntogris.recap.feature_recap.data.repository

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.dynamiclinks.ktx.socialMetaTagParameters
import com.google.firebase.firestore.Query
import com.puntogris.recap.R
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.data.remote.FirestoreRecapPagingSource
import com.puntogris.recap.core.utils.Constants.ANDROID_FALL_BACK_URL
import com.puntogris.recap.core.utils.Constants.DEEP_LINK_PATH
import com.puntogris.recap.core.utils.Constants.DOMAIN_URI_PREFIX
import com.puntogris.recap.core.utils.Constants.INTERACTIONS_COLLECTION
import com.puntogris.recap.core.utils.Constants.RECAPS_COLLECTION
import com.puntogris.recap.core.utils.Constants.SQUARE_APP_LOGO_URL
import com.puntogris.recap.core.utils.Constants.USERS_COLLECTION
import com.puntogris.recap.core.utils.RecapOrder
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.ReviewOrder
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapInteractions
import com.puntogris.recap.feature_recap.domain.model.Report
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RecapRepositoryImpl(
    private val firebase: FirebaseClients,
    private val recapDao: RecapDao,
    private val context: Context
) : RecapRepository {

    override suspend fun publishRecap(recap: Recap): Resource<String> = withContext(Dispatchers.IO) {
        Resource.build {
            val ref = firebase.firestore
                .collection(RECAPS_COLLECTION)
                .document()

            recap.apply {
                id = ref.id
                deepLink = generateRecapDynamicLink(title, id)
                ref.set(recap).await()
            }.deepLink
        }
    }

    private suspend fun generateRecapDynamicLink(title: String, id: String): String {
        return firebase.links.shortLinkAsync {
            link = Uri.parse(DEEP_LINK_PATH + id)
            domainUriPrefix = DOMAIN_URI_PREFIX

            androidParameters(context.packageName) {
                fallbackUrl = Uri.parse(ANDROID_FALL_BACK_URL)
            }

            socialMetaTagParameters {
                this.title = title
                description = context.getString(R.string.recap_link_title, title)
                imageUrl = Uri.parse(SQUARE_APP_LOGO_URL)
            }

        }.await().shortLink.toString()
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

    override fun getDraftsPaged(): Flow<PagingData<Recap>> {
        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { recapDao.getDraftsPaged() }.flow
    }

    override suspend fun getRecap(recapId: String): Resource<Recap> =
        withContext(Dispatchers.IO) {
            Resource.build {
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

    override suspend fun deleteDraft(recap: Recap): SimpleResource = withContext(Dispatchers.IO) {
        SimpleResource.build {
            recapDao.delete(recap.id)
        }
    }

    override suspend fun saveRecapDraft(recap: Recap) = withContext(Dispatchers.IO) {
        SimpleResource.build {
            recap.apply {
                id = firebase.firestore.collection("recaps").document().id
                recapDao.insert(this)
            }
        }
    }

    override suspend fun reportRecap(report: Report): SimpleResource =
        withContext(Dispatchers.IO) {
            SimpleResource.build {
                report.uid = firebase.currentUid()!!
                firebase.firestore.collection("reports").add(report)
            }
        }
}