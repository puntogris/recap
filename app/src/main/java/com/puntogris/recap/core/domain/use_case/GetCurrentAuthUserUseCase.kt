package com.puntogris.recap.core.domain.use_case

import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.core.data.remote.FirebaseClients

class GetCurrentAuthUserUseCase(
    private val firebaseClients: FirebaseClients
) {
    operator fun invoke(): FirebaseUser? {
        return firebaseClients.auth.currentUser
    }
}

fun GetCurrentAuthUserUseCase.isLoggedIn() = this() != null