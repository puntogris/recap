package com.puntogris.recap.data.repo.search

import androidx.paging.PagingData
import com.puntogris.recap.model.PublicProfile
import com.puntogris.recap.model.Recap
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun searchUsers(query: String): Flow<PagingData<PublicProfile>>
    fun searchRecaps(query: String): Flow<PagingData<Recap>>

}