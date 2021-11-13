package com.puntogris.recap.feature_profile.domain.use_case

import com.puntogris.recap.R
import com.puntogris.recap.core.domain.use_case.GetCurrentAuthUserUseCase
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository

class DeleteCurrentUserProfileUseCase(
    private val repository: ProfileRepository,
    private val getCurrentAuthUser: GetCurrentAuthUserUseCase
) {

    suspend operator fun invoke(email: String?): SimpleResource {
        val currentUser = getCurrentAuthUser()

        return if (currentUser == null || currentUser.email.isNullOrBlank()) {
            SimpleResource.Error(R.string.general_error_message)
        } else if (email.isNullOrBlank() || currentUser.email != email) {
            SimpleResource.Error(R.string.snack_account_email_not_matching)
        } else {
            repository.deleteProfile()
        }
    }
}