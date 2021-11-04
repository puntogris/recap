package com.puntogris.recap.feature_auth.domain.repository

import android.content.Intent
import androidx.activity.result.ActivityResult
import com.puntogris.recap.core.utils.LoginResult
import com.puntogris.recap.core.utils.SimpleResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun serverAuthWithGoogle(result: ActivityResult): Flow<LoginResult>
    suspend fun signOutUserFromServerAndGoogle(): SimpleResult
}