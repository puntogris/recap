package com.puntogris.recap.feature_auth.domain.repository

import com.google.firebase.auth.AuthCredential

interface AuthServerApi {
    suspend fun signInWithCredential(credential: AuthCredential)
    fun signOut()
}