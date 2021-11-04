package com.puntogris.recap.di

import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.feature_search.data.repository.SearchRepositoryImpl
import com.puntogris.recap.feature_search.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class SearchModule {
    @Provides
    fun providesSearchRepository(firebase: FirebaseClients): SearchRepository {
        return SearchRepositoryImpl(firebase)
    }

}