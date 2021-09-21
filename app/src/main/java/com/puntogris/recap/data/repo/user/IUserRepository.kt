package com.puntogris.recap.data.repo.user

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.models.PublicProfile
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.SimpleResult

interface IUserRepository {
    fun isUserLoggedIn(): Boolean
    fun getFirebaseUser():FirebaseUser?
    suspend fun getPublicProfileWithId(userId: String): Result<PublicProfile>
    suspend fun updateUserProfile(publicProfile: PublicProfile): SimpleResult
}