package com.puntogris.recap.feature_profile.domain.repository

import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import com.puntogris.recap.feature_recap.domain.model.Recap
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun isUserLoggedIn(): Boolean

    fun getFirebaseUser(): FirebaseUser?

    fun getRecapDraftsPaged(): Flow<PagingData<Recap>>

    fun getRecapsPaged(): Flow<PagingData<Recap>>

    suspend fun getPublicProfile(userId: String): Resource<PublicProfile>

    suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult

    suspend fun deleteProfile(): SimpleResource
}