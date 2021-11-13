package com.puntogris.recap.feature_auth.data.data_source.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.DocumentReference
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.core.utils.IDGenerator
import com.puntogris.recap.feature_auth.domain.repository.AuthServerApi
import kotlinx.coroutines.tasks.await

class FirebaseAuthApi(private val firebase: FirebaseClients) : AuthServerApi {

    override suspend fun signInAndCreateUser(credential: AuthCredential) {

        val authResult = firebase.auth.signInWithCredential(credential).await()
        val user = requireNotNull(authResult.user)

        val publicRef = firebase
            .firestore
            .collection(Constants.USERS_COLLECTION)
            .document(user.uid)

        val privateRef = publicRef
            .collection(Constants.PRIVATE_PROFILE_COLLECTION)
            .document(user.uid)

        if (!privateRef.get().await().exists()) {
            val username = IDGenerator.randomID()
            val usernameRef = firebase.firestore
                .collection(Constants.USERNAMES_COLLECTION)
                .document(username)

            firebase.firestore.runBatch {
                it.set(privateRef, user.toPrivate(username))
                it.set(publicRef, user.toPublic(username))
                it.set(usernameRef, Constants.UID_FIELD to user.uid)
            }.await()
        }
    }

    override fun signOut() {
        firebase.auth.signOut()
    }
}
