package com.puntogris.recap.feature_profile.presentation.edit_profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.use_case.UpdateProfileUseCase
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {

    private val _userProfile = MutableStateFlow(UpdateProfileData())
    val userProfile = _userProfile.asStateFlow()

    fun setUser(publicProfile: PublicProfile) {
        _userProfile.value.apply {
            name = publicProfile.name
            account = publicProfile.username
            bio = publicProfile.bio
            photoUrl = publicProfile.photoUrl
            lastEdited = publicProfile.lastEdited
        }
    }

    suspend fun saveProfileChanges(): EditProfileResult {
        return updateProfileUseCase(_userProfile.value)
    }

    fun updateProfileUri(uri: Uri?) {
        _userProfile.value.photoUrl = uri.toString()
    }

    fun updateProfileFields(name: String, username: String, bio: String) {
        _userProfile.value.apply {
            this.name = name
            this.account = username
            this.bio = bio
        }
    }
}