package com.puntogris.recap.feature_profile.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.feature_profile.domain.model.EditProfile
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.core.utils.Result
import com.puntogris.recap.core.utils.EditProfileResult

interface UserRepository {
    fun isUserLoggedIn(): Boolean
    fun getFirebaseUser(): FirebaseUser?
    suspend fun getPublicProfileWithId(userId: String): Result<Exception, PublicProfile>
    suspend fun updateUserProfile(editProfile: EditProfile): EditProfileResult
}