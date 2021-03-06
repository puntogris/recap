package com.puntogris.recap.feature_profile.domain.repository

import androidx.paging.PagingSource
import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapStatus

interface ProfileServerApi {
    suspend fun getProfile(userId: String): PublicProfile?
    suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult
    fun currentAuthUser(): FirebaseUser?
    fun getRecapsPagingSource(recapStatus: String): PagingSource<*, Recap>
    suspend fun deleteAccount()
}