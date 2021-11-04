package com.puntogris.recap.domain.repository

import android.content.Intent
import com.puntogris.recap.utils.LoginResult
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun serverAuthWithGoogle(idToken: String): Flow<LoginResult>
    fun getGoogleSignInIntent(): Intent
    suspend fun signOutUserFromServerAndGoogle(): SimpleResult
}