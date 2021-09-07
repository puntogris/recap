package com.puntogris.recap.data.repo.user

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.utils.SimpleResult

interface IUserRepository {
    fun isUserLoggedIn(): Boolean
    fun getFirebaseUser():FirebaseUser?

}