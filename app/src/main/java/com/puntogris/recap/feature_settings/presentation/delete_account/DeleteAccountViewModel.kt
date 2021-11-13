package com.puntogris.recap.feature_settings.presentation.delete_account

import androidx.lifecycle.ViewModel
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_auth.domain.user_case.LogoutUseCase
import com.puntogris.recap.feature_profile.domain.use_case.DeleteCurrentUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val deleteProfile: DeleteCurrentUserProfileUseCase,
    private val logout: LogoutUseCase,
) : ViewModel() {

    suspend fun deleteAccount(email: String): SimpleResource {
        return deleteProfile(email).let {
            if (it is SimpleResource.Success) return logout() else it
        }
    }

}