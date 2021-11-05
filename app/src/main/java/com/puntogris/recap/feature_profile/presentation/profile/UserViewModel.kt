package com.puntogris.recap.feature_profile.presentation.profile

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.puntogris.recap.core.domain.use_case.GetOwnUserIdUseCase
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository
import com.puntogris.recap.feature_search.domain.repository.SearchRepository
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.core.presentation.base.BaseRvViewModel
import com.puntogris.recap.core.utils.Result
import com.puntogris.recap.feature_profile.domain.use_case.GetDraftsForProfileUseCase
import com.puntogris.recap.feature_profile.domain.use_case.GetProfileUseCase
import com.puntogris.recap.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCases,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase
) : BaseRvViewModel() {

    private val _userProfile = MutableLiveData(PublicProfile())
    val userProfile: LiveData<PublicProfile> = _userProfile

    private val userId = MutableLiveData<String>()

    val recapsLiveData = Transformations.switchMap(userId) {
        profileUseCase.getRecaps().asLiveData()
    }.cachedIn(viewModelScope)

    val reviewsLiveData = Transformations.switchMap(userId) {
        profileUseCase.getRecaps().asLiveData()
    }.cachedIn(viewModelScope)

    fun getDrafts() = profileUseCase.getDrafts().cachedIn(viewModelScope)

    fun setUser(publicProfile: PublicProfile) {
        _userProfile.value = publicProfile
    }

    fun getUser(userId: String) {
        viewModelScope.launch {
            val result = profileUseCase.getProfile(userId)
            if (result is Result.Success) {
                setUser(result.value)
            }
        }
    }

    fun setUserId(userId: String) {
        this.userId.value = userId
    }

    fun isProfileFromCurrentUser(currentUid: String) = getOwnUserIdUseCase() == currentUid
}