package com.puntogris.recap.data.remote

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseDataSource @Inject constructor() {

    val firestore = Firebase.firestore
    val auth = Firebase.auth

    fun currentUid() = auth.uid

    fun signOut(){
        auth.signOut()
    }

}