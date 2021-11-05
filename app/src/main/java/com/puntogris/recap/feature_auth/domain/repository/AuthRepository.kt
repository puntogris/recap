package com.puntogris.recap.feature_auth.domain.repository

import androidx.activity.result.ActivityResult
import com.puntogris.recap.core.utils.SimpleResult
import com.puntogris.recap.feature_auth.presentation.util.LoginResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun serverAuthWithGoogle(result: ActivityResult): Flow<LoginResult>
    suspend fun logout(): SimpleResult
}