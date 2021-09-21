package com.puntogris.recap.data.repo.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.data.repo.FirestorePublicProfilePagingSource
import com.puntogris.recap.data.repo.FirestoreRecapPagingSource
import com.puntogris.recap.model.PublicProfile
import com.puntogris.recap.model.Recap
import com.puntogris.recap.utils.Constants.PUBLIC_PROFILE_COLLECTION
import com.puntogris.recap.utils.Constants.RECAPS_COLLECTION
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val firebase: FirebaseDataSource
): ISearchRepository {

    override fun searchRecaps(query: String): Flow<PagingData<Recap>> {
        val firebaseQuery = firebase
            .firestore
            .collection(RECAPS_COLLECTION)
            .orderBy("title").startAt(query).endAt("$query~")

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200)
        ){ FirestoreRecapPagingSource(firebaseQuery) }.flow
    }

    override fun searchUsers(query: String): Flow<PagingData<PublicProfile>> {
        val firebaseQuery = firebase
            .firestore
            .collection(PUBLIC_PROFILE_COLLECTION)

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200)
        ){ FirestorePublicProfilePagingSource(firebaseQuery) }.flow
    }
}