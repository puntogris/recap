package com.puntogris.recap.feature_profile.presentation.edit_profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.recap.feature_profile.domain.repository.UserRepository
import com.puntogris.recap.feature_profile.domain.model.EditProfile
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.core.utils.EditProfileResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private var initialProfile: PublicProfile? = null

    private val _userProfile = MutableLiveData(PublicProfile())
    val userProfile: LiveData<PublicProfile> = _userProfile

    fun setUser(publicProfile: PublicProfile){
        _userProfile.value = publicProfile
        initialProfile = publicProfile
    }

    suspend fun saveProfileChanges(): EditProfileResult {
        return if(profileDataChanged()){
            userRepository.updateUserProfile(getEditProfile())
        } else EditProfileResult.Success
    }

    private fun getEditProfile() = userProfile.value!!.run {
        val editProfile = EditProfile(uid = userProfile.value!!.uid)

        if (nameChanged()) editProfile.name = name
        if (bioChanged()) editProfile.bio = bio
        if (imageChanged()) editProfile.imageUri = photoUrl
        if (accountChanged()) editProfile.account = account

        editProfile
    }

    private fun nameChanged() = initialProfile?.name != userProfile.value!!.name

    private fun bioChanged() = initialProfile?.bio != userProfile.value!!.bio

    private fun imageChanged() = initialProfile?.photoUrl != userProfile.value!!.photoUrl

    private fun accountChanged() = initialProfile?.account != userProfile.value!!.account

    private fun profileDataChanged() =
        nameChanged() || bioChanged() || imageChanged() || accountChanged()

    fun updateProfileUri(uri: Uri?){

    }
}