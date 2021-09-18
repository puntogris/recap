package com.puntogris.recap.data.repo.recap

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.firestore.Query
import com.puntogris.recap.R
import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.data.repo.FirestoreRecapPagingSource
import com.puntogris.recap.models.Recap
import com.puntogris.recap.models.RecapInteractions
import com.puntogris.recap.utils.*
import com.puntogris.recap.utils.Constants.ANDROID_FALL_BACK_URL
import com.puntogris.recap.utils.Constants.DEEP_LINK_PATH
import com.puntogris.recap.utils.Constants.DOMAIN_URI_PREFIX
import com.puntogris.recap.utils.Constants.INTERACTIONS_COLLECTION
import com.puntogris.recap.utils.Constants.RECAPS_COLLECTION
import com.puntogris.recap.utils.Constants.SQUARE_APP_LOGO_URL
import com.puntogris.recap.utils.Constants.USERS_COLLECTION
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecapRepository @Inject constructor(
    private val firebase: FirebaseDataSource,
    @ApplicationContext private val context: Context
):IRecapRepository {

    override suspend fun saveRecapIntoDb(recap: Recap): SimpleResult  = withContext(Dispatchers.IO){
        try {
            val ref = firebase.firestore
                .collection(USERS_COLLECTION)
                .document(firebase.currentUid()!!)
                .collection(RECAPS_COLLECTION)
                .document()

            recap.id = ref.id
            recap.deepLink = generateRecapDynamicLink(recap)
            ref.set(recap).await()

            SimpleResult.Success
        }catch (E:Exception){
            SimpleResult.Failure
        }
    }

    private suspend fun generateRecapDynamicLink(recap: Recap): String{
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

    override fun getRecapsPagingData(order: RecapOrder): Flow<PagingData<Recap>> {
        val baseQuery = firebase
            .firestore
            .collection(RECAPS_COLLECTION)

        val query = when(order){
            RecapOrder.LATEST -> baseQuery.orderBy("created", Query.Direction.DESCENDING)
            RecapOrder.OLDEST -> baseQuery.orderBy("created", Query.Direction.ASCENDING)
            RecapOrder.POPULAR -> baseQuery.orderBy("likes", Query.Direction.DESCENDING)
        }

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200)
        ){ FirestoreRecapPagingSource(query) }.flow
    }

    override fun getReviewsPagingData(order: ReviewOrder): Flow<PagingData<Recap>> {
        val query = firebase
            .firestore
            .collection(RECAPS_COLLECTION)
            .whereEqualTo("aproved",false)

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200)
        ){ FirestoreRecapPagingSource(query) }.flow
    }

    override suspend fun getRecapWithId(recapId: String): Result<Recap> = withContext(Dispatchers.IO){
        try {
            val recap = firebase
                .firestore
                .collection(RECAPS_COLLECTION)
                .document(recapId)
                .get()
                .await()
                .toObject(Recap::class.java)

            Result.Success(recap!!)
        }catch (e:Exception){
            Result.Error(e)
        }
    }

    override suspend fun getUserRecapInteractions(recapId: String): RecapInteractions? = withContext(Dispatchers.IO){
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

}