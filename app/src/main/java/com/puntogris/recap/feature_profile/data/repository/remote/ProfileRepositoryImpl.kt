package com.puntogris.recap.feature_profile.data.repository.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.utils.DispatcherProvider
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository
import com.puntogris.recap.feature_profile.domain.repository.ProfileServerApi
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import com.puntogris.recap.feature_recap.data.data_source.toDomain
import com.puntogris.recap.feature_recap.domain.model.Recap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val profileServerApi: ProfileServerApi,
    private val recapDao: RecapDao,
    private val dispatcherProvider: DispatcherProvider
) : ProfileRepository {

    override fun isUserLoggedIn() = profileServerApi.currentAuthUser() != null

    override fun getFirebaseUser() = profileServerApi.currentAuthUser()

    override suspend fun getPublicProfile(userId: String): Resource<PublicProfile> =
        withContext(dispatcherProvider.io) {
            Resource.build {
                profileServerApi.getProfile(userId)
            }
        }

    override suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult =
        withContext(dispatcherProvider.io) {
            try {
                profileServerApi.updateUserProfile(updateProfileData)
            } catch (e: Exception) {
                EditProfileResult.Failure.Error
            }
        }

    override fun getRecapDraftsPaged(): Flow<PagingData<Recap>> {
        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { recapDao.getDraftsPaged() }.flow.map { data ->
            data.map { recap -> recap.toDomain() }
        }
    }

    override fun getRecapsPaged(): Flow<PagingData<Recap>> {
        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { profileServerApi.getProfileRecapsPagingSource() }.flow
    }
}