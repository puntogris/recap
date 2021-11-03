package com.puntogris.recap.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.puntogris.recap.domain.repository.LoginRepository
import com.puntogris.recap.domain.repository.RecapRepository
import com.puntogris.recap.domain.repository.UserRepository
import com.puntogris.recap.ui.base.BaseRvViewModel
import com.puntogris.recap.utils.RecapOrder
import com.puntogris.recap.utils.ReviewOrder
import com.puntogris.recap.utils.SimpleResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recapRepository: RecapRepository,
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
): BaseRvViewModel() {

    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?> = _userId

    private val _usernameLiveData = MutableLiveData<String?>()
    val usernameLiveData: LiveData<String?> = _usernameLiveData

    private val _emailLiveData = MutableLiveData<String?>()
    val emailLiveData: LiveData<String?> = _emailLiveData

    private val _profilePictureLiveData = MutableLiveData<String?>()
    val profilePictureLiveData: LiveData<String?> = _profilePictureLiveData

    private val _authorizedLiveData = MutableLiveData(userRepository.isUserLoggedIn())
    val authorizedLiveData: LiveData<Boolean> = _authorizedLiveData

    private val _recapOrder = MutableLiveData(RecapOrder.LATEST)
    val recapOrder: LiveData<RecapOrder> = _recapOrder

    private val _reviewOrder = MutableLiveData(ReviewOrder.ALL)
    val reviewOrder: LiveData<ReviewOrder> = _reviewOrder

    val recapsLiveData = Transformations.switchMap(recapOrder){
        recapRepository.getRecapsPagingData(it).asLiveData()
    }.cachedIn(viewModelScope)

    val reviewsLiveData = Transformations.switchMap(reviewOrder) {
        recapRepository.getReviewsPagingData(it).asLiveData()
    }.cachedIn(viewModelScope)

    fun orderRecapsBy(selection: Int) {
        RecapOrder.values().getOrNull(selection)?.let {
            _recapOrder.value = it
        }
    }

    fun orderReviewsBy(selection: Int) {
        ReviewOrder.values().getOrNull(selection)?.let {
            _reviewOrder.value = it
        }
    }

    fun isUserLoggedIn() = userRepository.isUserLoggedIn()

    private fun updateUser(username: String?, email: String?, profilePicture: String?, uid: String?) {
        _usernameLiveData.postValue(username)
        _emailLiveData.postValue(email)
        _profilePictureLiveData.postValue(profilePicture)
        _userId.postValue(uid)
    }

    fun refreshUserProfile() {
        val currentUser = userRepository.getFirebaseUser()
        if (currentUser != null) {
            _authorizedLiveData.postValue(true)
            updateUser(currentUser.displayName, currentUser.email, currentUser.photoUrl.toString(), currentUser.uid)
        }
    }

    suspend fun logOut(): SimpleResult {
        _authorizedLiveData.value = false
        updateUser(null, null, null, null)
        return loginRepository.signOutUserFromFirebaseAndGoogle()
    }
}