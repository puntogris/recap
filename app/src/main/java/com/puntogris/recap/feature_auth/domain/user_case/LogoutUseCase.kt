package com.puntogris.recap.feature_auth.domain.user_case

import androidx.activity.result.ActivityResult
import com.puntogris.recap.core.utils.SimpleResult
import com.puntogris.recap.feature_auth.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): SimpleResult {
        return authRepository.logout()
    }
}