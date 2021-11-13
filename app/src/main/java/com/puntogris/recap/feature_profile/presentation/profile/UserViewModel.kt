package com.puntogris.recap.feature_profile.presentation.profile

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.puntogris.recap.core.domain.use_case.GetOwnUserIdUseCase
import com.puntogris.recap.core.presentation.base.BaseRvViewModel
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.use_case.ProfileUseCases
import com.puntogris.recap.feature_recap.domain.model.RecapStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest
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
        profileUseCase.getRecaps(RecapStatus.APPROVED).asLiveData()
    }.cachedIn(viewModelScope)

    val reviewsLiveData = Transformations.switchMap(userId) {
        profileUseCase.getRecaps(RecapStatus.PENDING).asLiveData()
    }.cachedIn(viewModelScope)

    fun getDrafts() = profileUseCase.getDrafts().cachedIn(viewModelScope)

    fun setUser(publicProfile: PublicProfile) {
        _userProfile.value = publicProfile
    }

    fun getUser(userId: String) {
        viewModelScope.launch {
            val result = profileUseCase.getProfile(userId)
            if (result is Resource.Success) {
                setUser(result.data)
            }
        }
    }

    fun setUserId(userId: String) {
        this.userId.value = userId
    }

    fun isProfileFromCurrentUser(currentUid: String) = getOwnUserIdUseCase() == currentUid
}