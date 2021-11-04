package com.puntogris.recap.di

import android.content.Context
import androidx.room.Room
import com.puntogris.recap.core.data.local.AppDatabase
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.feature_auth.data.datasource.GoogleSingInDataSource
import com.puntogris.recap.core.data.repository.DraftRepositoryImpl
import com.puntogris.recap.core.domain.DraftRepository
import com.puntogris.recap.feature_auth.domain.repository.LoginRepository
import com.puntogris.recap.feature_auth.data.repository.LoginRepositoryImpl
import com.puntogris.recap.core.domain.RatingRepository
import com.puntogris.recap.core.data.repository.RatingRepositoryImpl
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import com.puntogris.recap.feature_recap.data.repository.RecapRepositoryImpl
import com.puntogris.recap.core.domain.ReportRepository
import com.puntogris.recap.core.data.repository.ReportRepositoryImpl
import com.puntogris.recap.feature_search.domain.repository.SearchRepository
import com.puntogris.recap.feature_search.data.repository.SearchRepositoryImpl
import com.puntogris.recap.feature_profile.domain.repository.UserRepository
import com.puntogris.recap.core.data.repository.UserRepositoryImpl
import com.puntogris.recap.core.utils.DispatcherProvider
import com.puntogris.recap.core.utils.StandardDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    private val roomName = "recap_database"

    @Provides
    fun providesRecapDao(appDatabase: AppDatabase) = appDatabase.recapDao()

    @Provides
    @Singleton
    fun provideReminderDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                roomName
            )
            .build()
    }

    @Provides
    fun providesDraftRepository(
        recapDao: RecapDao,
        firebase: FirebaseClients
    ): DraftRepository {
        return DraftRepositoryImpl(recapDao, firebase)
    }

    @Provides
    fun providesLoginRepository(
        firebase: FirebaseClients,
        googleSingInDataSource: GoogleSingInDataSource
    ): LoginRepository {
        return LoginRepositoryImpl(firebase, googleSingInDataSource)
    }

    @Provides
    fun providesRatingRepository(firebase: FirebaseClients): RatingRepository {
        return RatingRepositoryImpl(firebase)
    }

    @Provides
    fun providesRecapRepository(
        firebase: FirebaseClients,
        recapDao: RecapDao,
        @ApplicationContext context: Context
    ): RecapRepository {
        return RecapRepositoryImpl(firebase, recapDao, context)
    }

    @Provides
    fun providesReportRepository(firebase: FirebaseClients): ReportRepository {
        return ReportRepositoryImpl(firebase)
    }

    @Provides
    fun providesSearchRepository(firebase: FirebaseClients): SearchRepository {
        return SearchRepositoryImpl(firebase)
    }

    @Provides
    fun providesUserRepository(firebase: FirebaseClients,
                               @ApplicationContext context: Context
    ): UserRepository {
        return UserRepositoryImpl(firebase, context)
    }

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = StandardDispatchers()
}