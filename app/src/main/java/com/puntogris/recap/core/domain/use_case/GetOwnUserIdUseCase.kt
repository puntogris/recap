package com.puntogris.recap.core.domain.use_case

import com.puntogris.recap.core.data.remote.FirebaseClients

class GetOwnUserIdUseCase(
    private val firebaseClients: FirebaseClients
) {
    operator fun invoke(): String {
        return firebaseClients.auth.uid.toString()
    }
}