package com.puntogris.recap.feature_auth.data.repository

import androidx.activity.result.ActivityResult
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_auth.data.data_source.GoogleSingInDataSource
import com.puntogris.recap.feature_auth.domain.repository.AuthRepository
import com.puntogris.recap.feature_auth.domain.repository.AuthServerApi
import com.puntogris.recap.feature_auth.presentation.util.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authServerApi: AuthServerApi,
    private val googleSingIn: GoogleSingInDataSource
) : AuthRepository {

    override fun serverAuthWithGoogle(result: ActivityResult): Flow<LoginResult> = flow {
        try {
            emit(LoginResult.InProgress)
            val credential = googleSingIn.getCredentialWithIntent(result.data!!)
            authServerApi.signInWithCredential(credential)
            emit(LoginResult.Success)
        } catch (e: Exception) {
            emit(LoginResult.Error)
        }
    }

    override suspend fun logout() = SimpleResource.build {
        authServerApi.signOut()
        googleSingIn.signOut()
    }
}

