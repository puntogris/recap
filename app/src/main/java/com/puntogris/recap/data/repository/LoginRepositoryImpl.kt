package com.puntogris.recap.data.repository

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.puntogris.recap.BuildConfig
import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.domain.repository.LoginRepository
import com.puntogris.recap.utils.LoginResult
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginRepositoryImpl(
    private val context: Context,
    private val firebase: FirebaseDataSource
) : LoginRepository {

    private fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    override fun firebaseAuthWithGoogle(idToken: String): StateFlow<LoginResult> {
        val result = MutableStateFlow<LoginResult>(LoginResult.InProgress)
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebase.auth.signInWithCredential(credential)
            .addOnSuccessListener {
                //   val firestoreUser = getUserPrivateDataFromFirebaseUser(it.user)
                result.value = LoginResult.Success()
            }
            .addOnFailureListener { result.value = LoginResult.Error() }

        return result
    }


    override fun createGoogleSignInIntent(): Intent {
        return getGoogleSignInClient().signInIntent
    }

    override suspend fun signOutUserFromFirebaseAndGoogle() = SimpleResult.build {
        firebase.signOut()
        getGoogleSignInClient().signOut()
    }

}