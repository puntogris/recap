package com.puntogris.recap.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.puntogris.recap.models.Recap
import kotlinx.coroutines.tasks.await

class FirestoreRecapPagingSource(
    private val query: Query
) : PagingSource<QuerySnapshot, Recap>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Recap> {
        return try {
            val currentPage = params.key ?: query
                .get()
                .await()

            if (currentPage.size() != 0) {
                val lastDocumentSnapshot = currentPage.documents[currentPage.size() - 1]

                val nextPage = query.startAfter(lastDocumentSnapshot)
                    .get()
                    .await()

                LoadResult.Page(
                    data = currentPage.toObjects(Recap::class.java),
                    prevKey = null,
                    nextKey = nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Recap>): QuerySnapshot? {
        return null
    }
}