package com.puntogris.recap.core.domain.use_case

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.core.data.remote.FirebaseClients

class GetCurrentAuthUser(
    private val firebaseClients: FirebaseClients
) {
    operator fun invoke(): FirebaseUser? {
        return firebaseClients.auth.currentUser
    }
}

fun GetCurrentAuthUser.isLoggedIn() = this() != null