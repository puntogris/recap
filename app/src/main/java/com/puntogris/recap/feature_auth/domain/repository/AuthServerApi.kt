package com.puntogris.recap.feature_auth.domain.repository

import com.google.firebase.auth.AuthCredential

interface AuthServerApi {
    suspend fun signInAndCreateUser(credential: AuthCredential)
    fun signOut()
}