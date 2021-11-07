package com.puntogris.recap.feature_auth.data.data_source

import com.google.firebase.auth.AuthCredential
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.feature_auth.domain.repository.AuthServerApi
import kotlinx.coroutines.tasks.await

class FirebaseAuthApi(private val firebaseClients: FirebaseClients) : AuthServerApi {

    override suspend fun signInWithCredential(credential: AuthCredential) {
        firebaseClients.auth.signInWithCredential(credential).await()
    }

    override fun signOut() {
        firebaseClients.auth.signOut()
    }
}