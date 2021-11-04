package com.puntogris.recap.data.repository

import com.google.firebase.auth.GoogleAuthProvider
import com.puntogris.recap.data.remote.FirebaseClients
import com.puntogris.recap.data.remote.GoogleSingInDataSource
import com.puntogris.recap.domain.repository.LoginRepository
import com.puntogris.recap.utils.LoginResult
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(
    private val firebase: FirebaseClients,
    private val googleSingIn: GoogleSingInDataSource
) : LoginRepository {

    override fun serverAuthWithGoogle(idToken: String): Flow<LoginResult> = flow {
        try {
            emit(LoginResult.InProgress)
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebase.auth.signInWithCredential(credential).await()
            emit(LoginResult.Success)
        } catch (e: Exception) {
            emit(LoginResult.Error)
        }
    }

    override fun getGoogleSignInIntent() = googleSingIn.createSignIntent()

    override suspend fun signOutUserFromServerAndGoogle() = SimpleResult.build {
        firebase.signOut()
        googleSingIn.signOut()
    }

}