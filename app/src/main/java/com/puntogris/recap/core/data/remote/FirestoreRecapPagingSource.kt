package com.puntogris.recap.core.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.puntogris.recap.feature_recap.domain.model.Recap
import kotlinx.coroutines.tasks.await

class FirestoreRecapPagingSource(
    private val query: Query
) : PagingSource<QuerySnapshot, Recap>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Recap> {
        return try {
            val currentPage = params.key ?: query
                .get()
                .await()
            val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

            val nextPage = query.startAfter(lastDocumentSnapshot)
                .get()
                .await()

            LoadResult.Page(
                data = currentPage.toObjects(Recap::class.java),
                prevKey = null,
                nextKey = nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Recap>): QuerySnapshot? {
        return null
    }
}