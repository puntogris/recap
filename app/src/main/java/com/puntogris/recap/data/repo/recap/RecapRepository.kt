package com.puntogris.recap.data.repo.recap

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.firestore.Query
import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.data.repo.FirestoreRecapPagingSource
import com.puntogris.recap.models.Recap
import com.puntogris.recap.models.Report
import com.puntogris.recap.utils.RecapOrder
import com.puntogris.recap.utils.ReviewOrder
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecapRepository @Inject constructor(
    private val firebase: FirebaseDataSource
):IRecapRepository {

    override suspend fun saveRecapIntoDb(recap: Recap): SimpleResult  = withContext(Dispatchers.IO){
        try {
            val ref = firebase.firestore
                .collection("users")
                .document(firebase.currentUid()!!)
                .collection("recaps")
                .document()


            recap.id = ref.id
            ref.set(recap).await()

            SimpleResult.Success
        }catch (E:Exception){
            SimpleResult.Failure
        }
    }

    override fun getRecapsPagingData(order: RecapOrder): Flow<PagingData<Recap>> {
        val baseQuery = firebase
            .firestore
            .collection("recaps")

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
            .collection("recaps")
            .whereEqualTo("aproved",false)

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200)
        ){ FirestoreRecapPagingSource(query) }.flow
    }

    override suspend fun sendRecapReport(report: Report): SimpleResult = withContext(Dispatchers.IO){
        try {

            SimpleResult.Success
        }catch (e:Exception){
            SimpleResult.Failure
        }
    }

}