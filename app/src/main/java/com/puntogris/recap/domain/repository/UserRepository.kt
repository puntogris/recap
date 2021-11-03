package com.puntogris.recap.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.model.EditProfile
import com.puntogris.recap.model.PublicProfile
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.EditProfileResult

interface UserRepository {
    fun isUserLoggedIn(): Boolean
    fun getFirebaseUser(): FirebaseUser?
    suspend fun getPublicProfileWithId(userId: String): Result<Exception, PublicProfile>
    suspend fun updateUserProfile(editProfile: EditProfile): EditProfileResult
}