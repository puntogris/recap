package com.puntogris.recap.core.data.remote

import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClients @Inject constructor() {

    val firestore = Firebase.firestore
    val auth = Firebase.auth
    val links = Firebase.dynamicLinks
    val storage = Firebase.storage.reference
    val functions = Firebase.functions

    val currentUid: String
        get() = requireNotNull(auth.uid)
}