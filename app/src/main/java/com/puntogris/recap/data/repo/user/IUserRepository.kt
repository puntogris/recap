package com.puntogris.recap.data.repo.user

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.model.EditProfile
import com.puntogris.recap.model.PublicProfile
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.EditProfileResult

interface IUserRepository {
    fun isUserLoggedIn(): Boolean
    fun getFirebaseUser():FirebaseUser?
    suspend fun getPublicProfileWithId(userId: String): Result<PublicProfile>
    suspend fun updateUserProfile(editProfile: EditProfile): EditProfileResult
}