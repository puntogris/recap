package com.puntogris.recap.feature_auth.data.data_source.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.core.utils.IDGenerator
import com.puntogris.recap.feature_auth.domain.repository.AuthServerApi
import kotlinx.coroutines.tasks.await

class FirebaseAuthApi(private val firebase: FirebaseClients) : AuthServerApi {

    private val usersCollection = firebase.firestore.collection(Constants.USERS_COLLECTION)
    private val usernamesCollection = firebase.firestore.collection(Constants.USERNAMES_COLLECTION)

    override suspend fun signInAndCreateUser(credential: AuthCredential) {
        val authResult = firebase.auth.signInWithCredential(credential).await()
        createUser(requireNotNull(authResult.user))
    }

    private suspend fun createUser(user: FirebaseUser){
        val publicRef = usersCollection.document(user.uid)

        val privateRef = publicRef
            .collection(Constants.PRIVATE_PROFILE_COLLECTION)
            .document(user.uid)

        val usernameEmailRef = usernamesCollection.document(requireNotNull(user.email))

        firebase.firestore.runTransaction {
            if (it.get(publicRef).exists()) return@runTransaction

            val username =
                if (it.get(usernameEmailRef).exists()) {
                    IDGenerator.randomID()
                } else {
                    requireNotNull(user.email)
                }

            it.set(usernamesCollection.document(username), mapOf(Constants.UID_FIELD to user.uid))
            it.set(privateRef, user.toNewPrivateProfile())
            it.set(publicRef, user.toNewPublicProfile(username))
        }.await()
    }

    override fun signOut() {
        firebase.auth.signOut()
    }
}
