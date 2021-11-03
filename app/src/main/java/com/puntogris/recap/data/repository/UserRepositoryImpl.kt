package com.puntogris.recap.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.firebase.Timestamp
import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.domain.repository.UserRepository
import com.puntogris.recap.model.EditProfile
import com.puntogris.recap.model.PrivateProfile
import com.puntogris.recap.model.PublicProfile
import com.puntogris.recap.utils.Constants.PUBLIC_PROFILE_COLLECTION
import com.puntogris.recap.utils.Constants.PUBLIC_PROFILE_FIELD
import com.puntogris.recap.utils.Constants.USERS_COLLECTION
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.EditProfileResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class UserRepositoryImpl(
    private val firebase: FirebaseDataSource,
    private val context: Context
) : UserRepository {

    override fun isUserLoggedIn() = firebase.auth.currentUser != null

    override fun getFirebaseUser() = firebase.auth.currentUser

    override suspend fun getPublicProfileWithId(userId: String): Result<Exception, PublicProfile> =
        withContext(Dispatchers.IO) {
            Result.build {
                firebase.firestore
                    .collection(USERS_COLLECTION)
                    .document(userId)
                    .collection(PUBLIC_PROFILE_COLLECTION)
                    .limit(1)
                    .get()
                    .await()
                    .first()
                    .toObject(PublicProfile::class.java)
            }
        }

    override suspend fun updateUserProfile(editProfile: EditProfile): EditProfileResult =
        withContext(Dispatchers.IO) {
            try {
                val publicProfile = mutableMapOf<String, String>()
                val privateProfile = mutableMapOf<String, Timestamp>()

                val privateData = firebase.firestore
                    .collection(USERS_COLLECTION)
                    .document(firebase.currentUid()!!)
                    .get().await().toObject(PrivateProfile::class.java)!!

                editProfile.name?.let {
                    if (privateData.canEditName()) {
                        publicProfile["name"] = it
                        privateProfile["nameEdited"] = Timestamp.now()
                    } else return@withContext EditProfileResult.Failure.NameLimit
                }
                editProfile.bio?.let {
                    if (privateData.canEditBio()) {
                        publicProfile["bio"] = it
                        privateProfile["bioEdited"] = Timestamp.now()
                    } else return@withContext EditProfileResult.Failure.BioLimit
                }
                editProfile.account?.let {
                    if (privateData.canEditAccountId()) {
                        publicProfile["accountId"] = it
                        privateProfile["accountIdEdited"] = Timestamp.now()
                    } else return@withContext EditProfileResult.Failure.AccountIdLimit
                }
                editProfile.imageUri?.let {
                    if (privateData.canEditPhoto()) {
                        publicProfile["photoUrl"] = uploadImageAndGetUrl(it, editProfile.uid)
                        privateProfile["photoEdited"] = Timestamp.now()
                    } else return@withContext EditProfileResult.Failure.PhotoLimit
                }

                val publicRef =
                    firebase.firestore
                        .collection(USERS_COLLECTION)
                        .document(firebase.currentUid()!!)
                        .collection(PUBLIC_PROFILE_COLLECTION)
                        .document(PUBLIC_PROFILE_FIELD)

                val privateRef =
                    firebase.firestore
                        .collection(USERS_COLLECTION)
                        .document(firebase.currentUid()!!)

                firebase.firestore.runBatch {
                    it.update(publicRef, publicProfile.toMap())
                    it.update(privateRef, privateProfile.toMap())
                }.await()

                EditProfileResult.Success
            } catch (e: Exception) {
                EditProfileResult.Failure.Error
            }
        }

    private suspend fun uploadImageAndGetUrl(imageUri: String, uid: String): String {
        val data = compressImageFromUri(Uri.parse(imageUri))
        val storageRef = firebase.storage.child("users/${uid}/images/profile/image")
        val task = storageRef.putBytes(data).await().task

        return task.continueWithTask {
            if (!it.isSuccessful) it.exception?.let { e -> throw e }
            storageRef.downloadUrl
        }.await().toString()
    }

    private fun compressImageFromUri(uri: Uri): ByteArray {
        val baos = ByteArrayOutputStream()
        BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri)).let {
            it.compress(Bitmap.CompressFormat.JPEG, 50, baos)
            it.recycle()
        }
        return baos.toByteArray()
    }
}