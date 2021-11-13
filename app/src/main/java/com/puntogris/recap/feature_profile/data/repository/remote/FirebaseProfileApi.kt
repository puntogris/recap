package com.puntogris.recap.feature_profile.data.repository.remote

import android.content.Context
import android.net.Uri
import androidx.paging.PagingSource
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.lyft.kronos.KronosClock
import com.puntogris.recap.R
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.data.remote.FirestoreRecapPagingSource
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.core.utils.Utils
import com.puntogris.recap.core.utils.getTimestamp
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.repository.ProfileServerApi
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import com.puntogris.recap.feature_recap.domain.model.Recap
import kotlinx.coroutines.tasks.await

class FirebaseProfileApi(
    private val firebase: FirebaseClients,
    private val context: Context,
    private val kronosClock: KronosClock
) : ProfileServerApi {

    private val usersCollection = firebase.firestore.collection(Constants.USERS_COLLECTION)

    override fun currentAuthUser() = firebase.auth.currentUser

    override suspend fun getProfile(userId: String): PublicProfile? {
        return usersCollection
            .document(userId)
            .get()
            .await()
            .toObject(PublicProfile::class.java)
    }

    override suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult {

        val profileRef = usersCollection.document(requireNotNull(firebase.currentUid))

        val usernameRef = firebase.firestore
            .collection(Constants.USERNAMES_COLLECTION)
            .document(updateProfileData.username)

        if (usernameRef.get().await().exists()) {
            return EditProfileResult.Error(R.string.username_taken)
        }

        val profile = profileRef
            .get()
            .await()
            .toObject(PublicProfile::class.java)!!

        updateProfileData.apply {
            lastEdited = kronosClock.getTimestamp()
            if (photoUrl != profile.photoUrl) {
                photoUrl = uploadImageToServer(photoUrl, profile.uid)
            }
        }

        return firebase.firestore.runTransaction {
            return@runTransaction if (it.get(usernameRef).exists()) {
                EditProfileResult.Error(R.string.username_taken)
            } else {
                it.set(profileRef, updateProfileData, SetOptions.merge())
                it.delete(usernameRef)
                it.set(usernameRef, Constants.UID_FIELD to firebase.currentUid)
                EditProfileResult.Success
            }
        }.await()
    }

    private suspend fun uploadImageToServer(imageUri: String, uid: String): String {
        val data = Utils.compressImageFromUri(context, Uri.parse(imageUri))
        val storageRef = firebase.storage.child("users/${uid}/images/profile/image")
        storageRef.putBytes(data).await()

        return storageRef.downloadUrl.await().toString()
    }

    override fun getProfileRecapsPagingSource(): PagingSource<*, Recap> {
        val query = firebase.firestore
            .collection(Constants.RECAPS_COLLECTION)
            .whereEqualTo(Constants.UID_FIELD, requireNotNull(firebase.currentUid))
            .orderBy(Constants.CREATED_FIELD, Query.Direction.DESCENDING)

        return FirestoreRecapPagingSource(query)
    }
}