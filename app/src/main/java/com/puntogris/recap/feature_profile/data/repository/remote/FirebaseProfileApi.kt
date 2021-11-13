package com.puntogris.recap.feature_profile.data.repository.remote

import android.content.Context
import android.net.Uri
import androidx.paging.PagingSource
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.puntogris.recap.R
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.data.remote.FirestoreRecapPagingSource
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.core.utils.Utils
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.repository.ProfileServerApi
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapStatus
import kotlinx.coroutines.tasks.await

class FirebaseProfileApi(
    private val firebase: FirebaseClients,
    private val context: Context
) : ProfileServerApi {

    private fun getUsernameDocRef(document: String = firebase.currentUid): DocumentReference {
        return firebase.firestore.collection(Constants.USERNAMES_COLLECTION).document(document)
    }

    private fun getPublicRef(document: String = firebase.currentUid): DocumentReference {
        return firebase.firestore.collection(Constants.USERS_COLLECTION).document(document)
    }

    private fun getPrivateRef(document: String = firebase.currentUid): DocumentReference {
        return getPublicRef(document).collection(Constants.PRIVATE_PROFILE_COLLECTION)
            .document(document)
    }

    override fun currentAuthUser() = firebase.auth.currentUser

    override suspend fun getProfile(userId: String): PublicProfile? {
        return getPublicRef(userId)
            .get()
            .await()
            .toObject(PublicProfile::class.java)
    }

    /*
      We check twice if the new username is available to avoid uploading the photo if it isn't,
      once before the transaction an then inside it to ensure a unique username.
     */
    override suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult {
        val currentProfile = getPublicRef()
            .get()
            .await()
            .toObject(PublicProfile::class.java)!!

        val usernameRef = getUsernameDocRef(updateProfileData.username)

        updateProfileData.apply {
            if (currentProfile.username != username &&
                currentProfile.photoUrl != photoUrl &&
                usernameRef.get().await().exists()
            ) {
                return EditProfileResult.Error(R.string.username_taken)
            }
            if (photoUrl != currentProfile.photoUrl) {
                photoUrl = uploadImageToServer(photoUrl, currentProfile.uid)
            }
        }

        return firebase.firestore.runTransaction {
            if (it.get(usernameRef).exists()) {
                return@runTransaction EditProfileResult.Error(R.string.username_taken)
            }

            it.set(getPublicRef(), updateProfileData, SetOptions.merge())

            if (updateProfileData.username != currentProfile.username) {
                it.set(usernameRef, mapOf(Constants.UID_FIELD to firebase.currentUid))
                it.delete(getUsernameDocRef(currentProfile.username))
            }

            EditProfileResult.Success
        }.await()
    }

    private suspend fun uploadImageToServer(imageUri: String, uid: String): String {
        val data = Utils.compressImageFromUri(context, Uri.parse(imageUri))
        val storageRef = firebase.storage.child("users/${uid}/images/profile/image")

        return storageRef.let {
            it.putBytes(data).await()
            it.downloadUrl.await().toString()
        }
    }

    override fun getRecapsPagingSource(recapStatus: String): PagingSource<*, Recap> {
        val query = firebase.firestore
            .collection(Constants.RECAPS_COLLECTION)
            .whereEqualTo(Constants.UID_FIELD, firebase.currentUid)
            .orderBy(Constants.CREATED_FIELD, Query.Direction.DESCENDING)
            .whereEqualTo(Constants.STATUS_FIELD, recapStatus)

        return FirestoreRecapPagingSource(query)
    }

    override suspend fun deleteAccount() {
        firebase.firestore.runTransaction {
            val publicRef = getPublicRef()
            val privateRef = getPrivateRef()
            val username = it.get(publicRef).toObject(PublicProfile::class.java)?.username

            it.delete(publicRef)
            it.delete(privateRef)
            it.delete(getUsernameDocRef(requireNotNull(username)))
        }.await()
    }
}