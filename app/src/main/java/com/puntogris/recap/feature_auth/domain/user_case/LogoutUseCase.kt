package com.puntogris.recap.feature_auth.domain.user_case

import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_auth.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): SimpleResource {
        return authRepository.logout()
    }
}