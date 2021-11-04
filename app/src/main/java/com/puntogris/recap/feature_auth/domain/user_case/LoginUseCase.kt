package com.puntogris.recap.feature_auth.domain.user_case

import androidx.activity.result.ActivityResult
import com.puntogris.recap.core.utils.LoginResult
import com.puntogris.recap.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(activityResult: ActivityResult): Flow<LoginResult> {
        return authRepository.serverAuthWithGoogle(activityResult)
    }
}