package com.puntogris.recap.feature_profile.data.repository.remote

import androidx.paging.*
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.utils.DispatcherProvider
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.core.utils.Utils
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository
import com.puntogris.recap.feature_profile.domain.repository.ProfileServerApi
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import com.puntogris.recap.feature_recap.data.data_source.toDomain
import com.puntogris.recap.feature_recap.domain.model.Recap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val profileServerApi: ProfileServerApi,
    private val recapDao: RecapDao,
    private val dispatcher: DispatcherProvider
) : ProfileRepository {

    override fun isUserLoggedIn() = profileServerApi.currentAuthUser() != null

    override fun getFirebaseUser() = profileServerApi.currentAuthUser()

    override suspend fun getPublicProfile(userId: String): Resource<PublicProfile> =
        withContext(dispatcher.io) {
            Resource.build {
                requireNotNull(profileServerApi.getProfile(userId))
            }
        }

    override suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult =
        withContext(dispatcher.io) {
            try {
                profileServerApi.updateUserProfile(updateProfileData)
            } catch (e: Exception) {
                EditProfileResult.Error()
            }
        }

    override fun getRecapDraftsPaged(): Flow<PagingData<Recap>> {
        return Pager(Utils.defaultPagingConfig()) {
            recapDao.getDraftsPaged()
        }.flow.map { data ->
            data.map { recap -> recap.toDomain() }
        }
    }

    override fun getRecapsPaged(recapStatus: String): Flow<PagingData<Recap>> {
        return Pager(Utils.defaultPagingConfig()) {
            profileServerApi.getRecapsPagingSource(recapStatus)
        }.flow
    }

    override suspend fun deleteProfile(): SimpleResource = withContext(dispatcher.io) {
        SimpleResource.build {
            profileServerApi.deleteAccount()
        }
    }
}