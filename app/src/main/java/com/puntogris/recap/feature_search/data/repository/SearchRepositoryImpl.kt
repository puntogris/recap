package com.puntogris.recap.feature_search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.data.remote.FirestorePublicProfilePagingSource
import com.puntogris.recap.core.data.remote.FirestoreRecapPagingSource
import com.puntogris.recap.feature_search.domain.repository.SearchRepository
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.core.utils.Constants.PUBLIC_PROFILE_COLLECTION
import com.puntogris.recap.core.utils.Constants.RECAPS_COLLECTION
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl(private val firebase: FirebaseClients) : SearchRepository {

    override fun searchRecaps(query: String): Flow<PagingData<Recap>> {
        val firebaseQuery = firebase
            .firestore
            .collection(RECAPS_COLLECTION)
            .orderBy("title").startAt(query).endAt("$query~")

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { FirestoreRecapPagingSource(firebaseQuery) }.flow
    }

    override fun searchUsers(query: String): Flow<PagingData<PublicProfile>> {
        val firebaseQuery = firebase
            .firestore
            .collection(PUBLIC_PROFILE_COLLECTION)

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { FirestorePublicProfilePagingSource(firebaseQuery) }.flow
    }
}