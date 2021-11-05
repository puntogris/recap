package com.puntogris.recap.feature_profile.data.repository.remote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.Timestamp
import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.data.remote.FirestoreRecapPagingSource
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.core.utils.Constants.PUBLIC_PROFILE_COLLECTION
import com.puntogris.recap.core.utils.Constants.PUBLIC_PROFILE_FIELD
import com.puntogris.recap.core.utils.Constants.USERS_COLLECTION
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.feature_profile.domain.model.PrivateProfile
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import com.puntogris.recap.feature_recap.domain.model.Recap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class ProfileRepositoryImpl(
    private val firebase: FirebaseClients,
    private val context: Context,
    private val recapDao: RecapDao
) :
    ProfileRepository {

    override fun isUserLoggedIn() = firebase.auth.currentUser != null

    override fun getFirebaseUser() = firebase.auth.currentUser

    override suspend fun getPublicProfile(userId: String): Resource<PublicProfile> =
        withContext(Dispatchers.IO) {
            Resource.build {
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

    override suspend fun updateUserProfile(updateProfileData: UpdateProfileData): EditProfileResult =
        withContext(Dispatchers.IO) {
            try {
                val publicProfile = mutableMapOf<String, String>()
                val privateProfile = mutableMapOf<String, Timestamp>()

                val privateData = firebase.firestore
                    .collection(USERS_COLLECTION)
                    .document(firebase.currentUid()!!)
                    .get().await().toObject(PrivateProfile::class.java)!!

                updateProfileData.name?.let {
                    if (privateData.canEditName()) {
                        publicProfile["name"] = it
                        privateProfile["nameEdited"] = Timestamp.now()
                    } else return@withContext EditProfileResult.Failure.NameLimit
                }
                updateProfileData.bio?.let {
                    if (privateData.canEditBio()) {
                        publicProfile["bio"] = it
                        privateProfile["bioEdited"] = Timestamp.now()
                    } else return@withContext EditProfileResult.Failure.BioLimit
                }
                updateProfileData.account?.let {
                    if (privateData.canEditAccountId()) {
                        publicProfile["accountId"] = it
                        privateProfile["accountIdEdited"] = Timestamp.now()
                    } else return@withContext EditProfileResult.Failure.AccountIdLimit
                }
                updateProfileData.imageUri?.let {
                    if (privateData.canEditPhoto()) {
                        publicProfile["photoUrl"] = uploadImageAndGetUrl(it, updateProfileData.uid)
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

    override fun getRecapDraftsPaged(): Flow<PagingData<Recap>> {
        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { recapDao.getDraftsPaged() }.flow
    }

    override fun getRecapsPaged(): Flow<PagingData<Recap>> {
        val query = firebase
            .firestore
            .collection(Constants.RECAPS_COLLECTION)
            .whereEqualTo("approved", true)
            .whereEqualTo("uid", firebase.currentUid())

        return Pager(
            PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) { FirestoreRecapPagingSource(query) }.flow
    }
}