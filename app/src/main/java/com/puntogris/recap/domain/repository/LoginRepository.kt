package com.puntogris.recap.domain.repository

import android.content.Intent
import androidx.activity.result.ActivityResult
import com.puntogris.recap.utils.LoginResult
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun serverAuthWithGoogle(result: ActivityResult): Flow<LoginResult>
    fun getGoogleSignInIntent(): Intent
    suspend fun signOutUserFromServerAndGoogle(): SimpleResult
}