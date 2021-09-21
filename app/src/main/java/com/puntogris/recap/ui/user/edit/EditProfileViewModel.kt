package com.puntogris.recap.ui.user.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.recap.data.repo.user.UserRepository
import com.puntogris.recap.models.PublicProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _userProfile = MutableLiveData<PublicProfile>(PublicProfile())
    val userProfile: LiveData<PublicProfile> = _userProfile

    fun setUser(publicProfile: PublicProfile){
        _userProfile.value = publicProfile
    }

    suspend fun saveProfileChanges() = userRepository.updateUserProfile(userProfile.value!!)
}