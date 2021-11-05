package com.puntogris.recap.feature_auth.data.repository

import androidx.activity.result.ActivityResult
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_auth.data.datasource.GoogleSingInDataSource
import com.puntogris.recap.feature_auth.domain.repository.AuthRepository
import com.puntogris.recap.feature_auth.presentation.util.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebase: FirebaseClients,
    private val googleSingIn: GoogleSingInDataSource
) : AuthRepository {

    override fun serverAuthWithGoogle(result: ActivityResult): Flow<LoginResult> = flow {
        try {
            emit(LoginResult.InProgress)
            val credential = googleSingIn.getCredentialWithIntent(result.data!!)
            firebase.auth.signInWithCredential(credential).await()
            emit(LoginResult.Success)
        } catch (e: Exception) {
            emit(LoginResult.Error)
        }
    }

    override suspend fun logout() = SimpleResource.build {
        firebase.signOut()
        googleSingIn.signOut()
    }
}

