package com.puntogris.recap.di

import android.content.Context
import androidx.room.Room
import com.lyft.kronos.AndroidClockFactory
import com.lyft.kronos.KronosClock
import com.puntogris.recap.core.data.local.AppDatabase
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.domain.use_case.GetCurrentAuthUserUseCase
import com.puntogris.recap.core.domain.use_case.GetOwnUserIdUseCase
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

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = StandardDispatchers()

    @Provides
    @Singleton
    fun provideGetOwnUserIdUseCase(firebase: FirebaseClients): GetOwnUserIdUseCase {
        return GetOwnUserIdUseCase(firebase)
    }

    @Provides
    @Singleton
    fun provideGetCurrentAuthUser(firebase: FirebaseClients): GetCurrentAuthUserUseCase {
        return GetCurrentAuthUserUseCase(firebase)
    }

    @Provides
    @Singleton
    fun provideKronosClock(@ApplicationContext context: Context): KronosClock {
        return AndroidClockFactory.createKronosClock(context)
    }
}