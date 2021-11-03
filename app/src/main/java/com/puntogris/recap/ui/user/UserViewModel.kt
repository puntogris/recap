package com.puntogris.recap.ui.user

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.puntogris.recap.domain.repository.RecapRepository
import com.puntogris.recap.domain.repository.SearchRepository
import com.puntogris.recap.domain.repository.UserRepository
import com.puntogris.recap.model.PublicProfile
import com.puntogris.recap.model.Recap
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

    private val _userProfile = MutableLiveData(PublicProfile())
    val userProfile: LiveData<PublicProfile> = _userProfile

    private val userId = MutableLiveData<String>()

    val recapsLiveData = Transformations.switchMap(userId) {
        searchRepository.searchRecaps("the").asLiveData()
    }.cachedIn(viewModelScope)

    val reviewsLiveData = Transformations.switchMap(userId){
        searchRepository.searchUsers(it).asLiveData()
    }.cachedIn(viewModelScope)

    fun getDraftsLiveData() =
        recapRepository.getDraftsPagingData().asLiveData().cachedIn(viewModelScope)

    fun setUser(publicProfile: PublicProfile){
        _userProfile.value = publicProfile
    }

    fun getUser(userId: String){
        viewModelScope.launch {
            val result = userRepository.getPublicProfileWithId(userId)
            if (result is Result.Success){
                setUser(result.value)
            }
        }
    }

    fun setUserId(userId: String){
        this.userId.value = userId
    }

    fun isProfileFromCurrentUser(currentUid: String) = userRepository.getFirebaseUser()?.uid == currentUid

    suspend fun deleteDraft(recap: Recap) = recapRepository.deleteDraft(recap)

    suspend fun undoDraftDeletion(recap: Recap) = recapRepository.saveRecapLocalDb(Recap())

}