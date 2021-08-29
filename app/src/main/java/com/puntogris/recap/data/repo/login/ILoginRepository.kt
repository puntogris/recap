package com.puntogris.recap.data.repo.login

import android.content.Intent
import com.puntogris.recap.utils.LoginResult
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.flow.StateFlow

interface ILoginRepository {
    fun firebaseAuthWithGoogle(idToken: String): StateFlow<LoginResult>
    fun createGoogleSignInIntent(): Intent
    suspend fun signOutUserFromFirebaseAndGoogle(): SimpleResult
}