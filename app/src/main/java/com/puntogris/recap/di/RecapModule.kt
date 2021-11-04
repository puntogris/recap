package com.puntogris.recap.di

import android.content.Context
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.feature_recap.data.repository.RecapRepositoryImpl
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

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

}