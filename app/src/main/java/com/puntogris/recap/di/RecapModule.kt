package com.puntogris.recap.di

import android.content.Context
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.feature_recap.data.data_source.remote.FirebaseRecapApi
import com.puntogris.recap.feature_recap.data.repository.RecapRepositoryImpl
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import com.puntogris.recap.feature_recap.domain.repository.RecapServerApi
import com.puntogris.recap.feature_recap.domain.use_case.*
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
        recapDao: RecapDao,
        recapServerApi: RecapServerApi
    ): RecapRepository {
        return RecapRepositoryImpl(recapDao, recapServerApi)
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

    @Singleton
    @Provides
    fun provideReportRecapUseCase(repository: RecapRepository): ReportRecapUseCase {
        return ReportRecapUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetRecapUseCase(repository: RecapRepository): GetRecapUseCase {
        return GetRecapUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveRecapDraftUseCase(repository: RecapRepository): SaveRecapDraftUseCase {
        return SaveRecapDraftUseCase(repository)
    }

    @Singleton
    @Provides
    fun providePublishRecapUseCase(repository: RecapRepository): PublishRecapUseCase {
        return PublishRecapUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideRecapServerApi(
        firebaseClients: FirebaseClients,
        @ApplicationContext context: Context
    ): RecapServerApi {
        return FirebaseRecapApi(
            firebase = firebaseClients,
            context = context
        )
    }
}