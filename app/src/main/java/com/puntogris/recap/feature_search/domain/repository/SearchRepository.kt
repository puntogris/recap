package com.puntogris.recap.feature_search.domain.repository

import androidx.paging.PagingData
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_recap.domain.model.Recap
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchUsers(query: String): Flow<PagingData<PublicProfile>>
    fun searchRecaps(query: String): Flow<PagingData<Recap>>

}