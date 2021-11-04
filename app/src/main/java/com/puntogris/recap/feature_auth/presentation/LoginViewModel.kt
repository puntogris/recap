package com.puntogris.recap.feature_auth.presentation

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.puntogris.recap.feature_auth.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository):ViewModel() {

    fun authGoogleUser(result: ActivityResult) = repository.serverAuthWithGoogle(result)

    fun getGoogleSignInIntent() = repository.getGoogleSignInIntent()
}