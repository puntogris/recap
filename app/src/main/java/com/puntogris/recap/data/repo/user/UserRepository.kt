package com.puntogris.recap.data.repo.user

import com.puntogris.recap.data.remote.FirebaseDataSource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebase: FirebaseDataSource
): IUserRepository{
    override fun isUserLoggedIn() = firebase.auth.currentUser != null
}