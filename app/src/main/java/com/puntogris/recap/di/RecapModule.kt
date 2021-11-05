package com.puntogris.recap.di

import android.content.Context
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.feature_recap.data.repository.RecapRepositoryImpl
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import com.puntogris.recap.feature_recap.domain.use_case.GetRecapInteractionsUseCase
import com.puntogris.recap.feature_recap.domain.use_case.GetRecapsUseCase
import com.puntogris.recap.feature_recap.domain.use_case.GetReviewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RecapModule {

    @Provides
    fun providesRecapRepository(
        firebase: FirebaseClients,
        recapDao: RecapDao,
        @ApplicationContext context: Context
    ): RecapRepository {
        return RecapRepositoryImpl(firebase, recapDao, context)
    }

    @Singleton
    @Provides
    fun provideGetRecapsUseCase(repository: RecapRepository): GetRecapsUseCase {
        return GetRecapsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetReviewsUseCase(repository: RecapRepository): GetReviewsUseCase {
        return GetReviewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetRecapInteractionsUseCase(repository: RecapRepository): GetRecapInteractionsUseCase {
        return GetRecapInteractionsUseCase(repository)
    }


}