package com.puntogris.recap.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puntogris.recap.data.repo.user.UserRepository
import com.puntogris.recap.models.PublicProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    val userProfile = MutableLiveData(PublicProfile(
        name = "Joaquin Jk",
        bio = "Best recaps in the world"
    ))

}