package com.puntogris.recap.feature_profile.data.repository.remote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.firebase.Timestamp
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.core.utils.Utils
import com.puntogris.recap.feature_profile.domain.model.PrivateProfile
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.repository.ProfileServerApi
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream

class FirebaseProfileApi(
    private val firebase: FirebaseClients,
    private val context: Context
) : ProfileServerApi {

    override suspend fun getProfile(userId: String): PublicProfile {
        return firebase.firestore
            .collection(Constants.USERS_COLLECTION)
            .document(userId)
            .collection(Constants.PUBLIC_PROFILE_COLLECTION)
            .limit(1)
            .get()
            .await()
            .first()
            .toObject(PublicProfile::class.java)
    }

    override suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult {
        val publicProfile = mutableMapOf<String, String>()
        val privateProfile = mutableMapOf<String, Timestamp>()

        val privateData = firebase.firestore
            .collection(Constants.USERS_COLLECTION)
            .document(firebase.currentUid()!!)
            .get().await().toObject(PrivateProfile::class.java)!!

        updateProfileData.name?.let {
            if (privateData.canEditName()) {
                publicProfile["name"] = it
                privateProfile["nameEdited"] = Timestamp.now()
            } else return EditProfileResult.Failure.NameLimit
        }
        updateProfileData.bio?.let {
            if (privateData.canEditBio()) {
                publicProfile["bio"] = it
                privateProfile["bioEdited"] = Timestamp.now()
            } else return EditProfileResult.Failure.BioLimit
        }
        updateProfileData.account?.let {
            if (privateData.canEditAccountId()) {
                publicProfile["accountId"] = it
                privateProfile["accountIdEdited"] = Timestamp.now()
            } else return EditProfileResult.Failure.AccountIdLimit
        }
        updateProfileData.imageUri?.let {
            if (privateData.canEditPhoto()) {
                publicProfile["photoUrl"] = uploadImageAndGetUrl(it, updateProfileData.uid)
                privateProfile["photoEdited"] = Timestamp.now()
            } else return EditProfileResult.Failure.PhotoLimit
        }

        val publicRef =
            firebase.firestore
                .collection(Constants.USERS_COLLECTION)
                .document(firebase.currentUid()!!)
                .collection(Constants.PUBLIC_PROFILE_COLLECTION)
                .document(Constants.PUBLIC_PROFILE_FIELD)

        val privateRef =
            firebase.firestore
                .collection(Constants.USERS_COLLECTION)
                .document(firebase.currentUid()!!)

        firebase.firestore.runBatch {
            it.update(publicRef, publicProfile.toMap())
            it.update(privateRef, privateProfile.toMap())
        }.await()

        return EditProfileResult.Success
    }

    private suspend fun uploadImageAndGetUrl(imageUri: String, uid: String): String {
        val data = Utils.compressImageFromUri(context, Uri.parse(imageUri))
        val storageRef = firebase.storage.child("users/${uid}/images/profile/image")
        val task = storageRef.putBytes(data).await().task

        return task.continueWithTask {
            if (!it.isSuccessful) it.exception?.let { e -> throw e }
            storageRef.downloadUrl
        }.await().toString()
    }

    override fun currentAuthUser() = firebase.auth.currentUser
}