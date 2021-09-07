package com.puntogris.recap.data.repo.user

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.utils.SimpleResult
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebase: FirebaseDataSource
): IUserRepository{

    override fun isUserLoggedIn() = firebase.auth.currentUser != null

    override fun getFirebaseUser() = firebase.auth.currentUser

}