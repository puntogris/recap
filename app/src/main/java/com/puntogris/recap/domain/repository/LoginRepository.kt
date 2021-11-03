package com.puntogris.recap.domain.repository

import android.content.Intent
import com.puntogris.recap.utils.LoginResult
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.flow.StateFlow

interface LoginRepository {
    fun firebaseAuthWithGoogle(idToken: String): StateFlow<LoginResult>
    fun createGoogleSignInIntent(): Intent
    suspend fun signOutUserFromFirebaseAndGoogle(): SimpleResult
}