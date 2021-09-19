package com.puntogris.recap.data.repo.user

import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.models.PublicProfile
import com.puntogris.recap.utils.Constants.PUBLIC_PROFILES_GROUP_COLLECTION
import com.puntogris.recap.utils.Constants.USERS_COLLECTION
import com.puntogris.recap.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebase: FirebaseDataSource
): IUserRepository{

    override fun isUserLoggedIn() = firebase.auth.currentUser != null

    override fun getFirebaseUser() = firebase.auth.currentUser

    override suspend fun getPublicProfileWithId(userId: String): Result<PublicProfile> = withContext(Dispatchers.IO){
        try {
            val profile = firebase.firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .collection(PUBLIC_PROFILES_GROUP_COLLECTION)
                .limit(1)
                .get()
                .await()
                .first()
                .toObject(PublicProfile::class.java)

            Result.Success(profile)
        } catch (e:Exception){
            Result.Error(e)
        }
    }

}