package com.puntogris.recap.feature_profile.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult

interface ProfileServerApi {
    suspend fun getProfile(userId: String): PublicProfile
    suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult
    fun currentAuthUser(): FirebaseUser?
}