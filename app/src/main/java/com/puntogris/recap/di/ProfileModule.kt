package com.puntogris.recap.di

import android.content.Context
import com.lyft.kronos.KronosClock
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.domain.use_case.GetCurrentAuthUserUseCase
import com.puntogris.recap.core.utils.StandardDispatchers
import com.puntogris.recap.feature_profile.data.repository.remote.FirebaseProfileApi
import com.puntogris.recap.feature_profile.data.repository.remote.ProfileRepositoryImpl
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository
import com.puntogris.recap.feature_profile.domain.repository.ProfileServerApi
import com.puntogris.recap.feature_profile.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ProfileModule {

    @Provides
    fun providesProfileRepository(
        profileServerApi: ProfileServerApi,
        recapDao: RecapDao,
        standardDispatchers: StandardDispatchers
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileServerApi, recapDao, standardDispatchers)
    }

    @Provides
    @Singleton
    fun provideGetProfileUseCase(repository: ProfileRepository): GetProfileUseCase {
        return GetProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        repository: ProfileRepository,
        kronosClock: KronosClock
    ): UpdateProfileUseCase {
        return UpdateProfileUseCase(repository, kronosClock)
    }

    @Provides
    @Singleton
    fun provideGetDraftsForProfileUseCase(repository: ProfileRepository): GetDraftsForProfileUseCase {
        return GetDraftsForProfileUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetRecapsForProfileUseCase(repository: ProfileRepository): GetRecapsForProfileUseCase {
        return GetRecapsForProfileUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideProfileUsesCases(
        getProfileUseCase: GetProfileUseCase,
        updateProfileUseCase: UpdateProfileUseCase,
        getRecapsForProfileUseCase: GetRecapsForProfileUseCase,
        getDraftsForProfileUseCase: GetDraftsForProfileUseCase
    ): ProfileUseCases {
        return ProfileUseCases(
            getProfile = getProfileUseCase,
            updateProfile = updateProfileUseCase,
            getRecaps = getRecapsForProfileUseCase,
            getDrafts = getDraftsForProfileUseCase
        )
    }

    @Singleton
    @Provides
    fun provideProfileServerApi(
        firebaseClients: FirebaseClients,
        @ApplicationContext context: Context
    ): ProfileServerApi {
        return FirebaseProfileApi(
            firebase = firebaseClients,
            context = context
        )
    }

    @Singleton
    @Provides
    fun provideDeleteProfileUserCase(
        repository: ProfileRepository,
        getCurrentAuthUserUseCase: GetCurrentAuthUserUseCase
    ): DeleteCurrentUserProfileUseCase{
        return DeleteCurrentUserProfileUseCase(repository, getCurrentAuthUserUseCase)
    }
}