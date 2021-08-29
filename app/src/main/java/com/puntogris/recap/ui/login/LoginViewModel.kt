package com.puntogris.recap.ui.login

import androidx.lifecycle.ViewModel
import com.puntogris.recap.data.repo.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
):ViewModel() {

    fun authUserWithFirebase(idToken: String) = loginRepository.firebaseAuthWithGoogle(idToken)

    fun getGoogleSignInIntent() = loginRepository.createGoogleSignInIntent()
}