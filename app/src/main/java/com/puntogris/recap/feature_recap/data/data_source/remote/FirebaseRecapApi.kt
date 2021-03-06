package com.puntogris.recap.feature_recap.data.data_source.remote

import android.content.Context
import android.net.Uri
import androidx.paging.PagingSource
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.dynamiclinks.ktx.socialMetaTagParameters
import com.google.firebase.firestore.Query
import com.puntogris.recap.R
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.data.remote.FirestoreRecapPagingSource
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_recap.data.data_source.local.RecapEntity
import com.puntogris.recap.feature_recap.domain.model.*
import com.puntogris.recap.feature_recap.domain.repository.RecapServerApi
import kotlinx.coroutines.tasks.await

class FirebaseRecapApi(
    private val firebase: FirebaseClients,
    private val context: Context
) : RecapServerApi {

    private val recapCollection = firebase.firestore.collection(Constants.RECAPS_COLLECTION)

    override fun getRecapsPagingSource(order: RecapOrder): PagingSource<*, Recap> {
        val query = recapCollection
            .whereEqualTo(Constants.STATUS_FIELD, RecapStatus.APPROVED)
            .apply {
                when (order) {
                    RecapOrder.LATEST -> orderBy(
                        Constants.CREATED_FIELD,
                        Query.Direction.DESCENDING
                    )
                    RecapOrder.OLDEST -> orderBy(
                        Constants.CREATED_FIELD,
                        Query.Direction.ASCENDING
                    )
                    RecapOrder.POPULAR -> orderBy(
                        Constants.LIKED_FIELD,
                        Query.Direction.DESCENDING
                    )
                }
            }

        return FirestoreRecapPagingSource(query)
    }

    override fun getReviewsPagingSource(order: ReviewOrder): Pair<PagingSource<*, Recap>, String?> {
        val query = recapCollection
            .whereEqualTo(Constants.STATUS_FIELD, RecapStatus.PENDING)
        //TODO commented for testing, remove later
        //if (firebase.currentUid != null) query.whereNotEqualTo(Constants.AUTHOR_FILED, firebase.currentUid)

        return FirestoreRecapPagingSource(query) to firebase.currentUid
    }

    override suspend fun getRecap(recapId: String): Recap {
        val recap = recapCollection
            .document(recapId)
            .get()
            .await()
            .toObject(Recap::class.java)

        return requireNotNull(recap)
    }

    override suspend fun publishRecap(recap: RecapEntity): String {
        val ref = recapCollection.document()

        return recap.apply {
            id = ref.id
            uid = firebase.currentUid
            username = getCurrentUsername()
            deepLink = generateDynamicLink(title, id)

            ref.set(this).await()
        }.deepLink
    }

    private suspend fun getCurrentUsername(): String {
        val user = firebase.firestore
            .collection(Constants.USERS_COLLECTION)
            .document(firebase.currentUid)
            .get()
            .await()
            .toObject(PublicProfile::class.java)

        return requireNotNull(user).username
    }

    private suspend fun generateDynamicLink(recapTitle: String, recapId: String): String {
        val result = firebase.links.shortLinkAsync {
            link = Uri.parse(Constants.DEEP_LINK_PATH + recapId)
            domainUriPrefix = Constants.DOMAIN_URI_PREFIX

            androidParameters(context.packageName) {
                fallbackUrl = Uri.parse(Constants.ANDROID_FALL_BACK_URL)
            }

            socialMetaTagParameters {
                title = recapTitle
                description = context.getString(R.string.recap_link_title, recapTitle)
                imageUrl = Uri.parse(Constants.SQUARE_APP_LOGO_URL)
            }

        }.await()

        return result.shortLink.toString()
    }

    override suspend fun reportRecap(report: Report) {
        report.uid = firebase.currentUid
        firebase.firestore.collection(Constants.REPORTS_COLLECTION).add(report)
    }

    override suspend fun getRecapInteractionsWithCurrentUser(recapId: String): RecapInteractions? {
        return recapCollection
            .document(firebase.currentUid)
            .collection(Constants.INTERACTIONS_COLLECTION)
            .document(recapId)
            .get()
            .await()
            .toObject(RecapInteractions::class.java)
    }

    override suspend fun rateRecap(recapId: String, score: Int) {
        val data = hashMapOf(
            "recapId" to recapId,
            "score" to score,
            "reviewerId" to firebase.currentUid
        )

        firebase.functions
            .getHttpsCallable(Constants.RATE_HTTPS_FUNCTION)
            .call(data)
            .await()
    }
}