package com.puntogris.recap.ui.user

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.puntogris.recap.data.repo.recap.RecapRepository
import com.puntogris.recap.data.repo.search.SearchRepository
import com.puntogris.recap.data.repo.user.UserRepository
import com.puntogris.recap.models.PublicProfile
import com.puntogris.recap.ui.base.BaseRvViewModel
import com.puntogris.recap.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val searchRepository: SearchRepository,
    private val recapRepository: RecapRepository
): BaseRvViewModel() {

    private val _userProfile = MutableLiveData<PublicProfile>(PublicProfile())
    val userProfile: LiveData<PublicProfile> = _userProfile

    private val userId = MutableLiveData<String>()

    val recapsLiveData = Transformations.switchMap(userId) {
        searchRepository.searchRecaps("the").asLiveData()
    }.cachedIn(viewModelScope)

    val reviewsLiveData = Transformations.switchMap(userId){
        searchRepository.searchUsers(it).asLiveData()
    }.cachedIn(viewModelScope)

    fun setUser(publicProfile: PublicProfile){
        _userProfile.value = publicProfile
    }

    fun getUser(userId: String){
        viewModelScope.launch {
            val result = userRepository.getPublicProfileWithId(userId)
            if (result is Result.Success){
                setUser(result.data)
            }
        }
    }

    fun setUserId(userId: String){
        this.userId.value = userId
    }

}