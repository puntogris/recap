package com.puntogris.recap.data.repo.user

import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.models.PublicProfile
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun isUserLoggedIn(): Boolean
    fun getFirebaseUser():FirebaseUser?
}