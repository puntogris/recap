package com.puntogris.recap.di

import android.content.Context
import androidx.room.Room
import com.puntogris.recap.data.local.AppDatabase
import com.puntogris.recap.data.local.RecapDao
import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.data.repository.DraftRepositoryImpl
import com.puntogris.recap.domain.repository.DraftRepository
import com.puntogris.recap.domain.repository.LoginRepository
import com.puntogris.recap.data.repository.LoginRepositoryImpl
import com.puntogris.recap.domain.repository.RatingRepository
import com.puntogris.recap.data.repository.RatingRepositoryImpl
import com.puntogris.recap.domain.repository.RecapRepository
import com.puntogris.recap.data.repository.RecapRepositoryImpl
import com.puntogris.recap.domain.repository.ReportRepository
import com.puntogris.recap.data.repository.ReportRepositoryImpl
import com.puntogris.recap.domain.repository.SearchRepository
import com.puntogris.recap.data.repository.SearchRepositoryImpl
import com.puntogris.recap.domain.repository.UserRepository
import com.puntogris.recap.data.repository.UserRepositoryImpl
import com.puntogris.recap.utils.DispatcherProvider
import com.puntogris.recap.utils.StandardDispatchers
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
        firebase: FirebaseDataSource
    ): DraftRepository {
        return DraftRepositoryImpl(recapDao, firebase)
    }

    @Provides
    fun providesLoginRepository(
        @ApplicationContext context: Context,
        firebase: FirebaseDataSource
    ): LoginRepository {
        return LoginRepositoryImpl(context, firebase)
    }

    @Provides
    fun providesRatingRepository(firebase: FirebaseDataSource): RatingRepository {
        return RatingRepositoryImpl(firebase)
    }

    @Provides
    fun providesRecapRepository(
        firebase: FirebaseDataSource,
        recapDao: RecapDao,
        @ApplicationContext context: Context
    ): RecapRepository {
        return RecapRepositoryImpl(firebase, recapDao, context)
    }

    @Provides
    fun providesReportRepository(firebase: FirebaseDataSource): ReportRepository {
        return ReportRepositoryImpl(firebase)
    }

    @Provides
    fun providesSearchRepository(firebase: FirebaseDataSource): SearchRepository {
        return SearchRepositoryImpl(firebase)
    }

    @Provides
    fun providesUserRepository(firebase: FirebaseDataSource,
        @ApplicationContext context: Context
    ): UserRepository {
        return UserRepositoryImpl(firebase, context)
    }

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = StandardDispatchers()
}