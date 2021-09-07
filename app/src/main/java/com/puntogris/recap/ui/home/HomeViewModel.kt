package com.puntogris.recap.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.google.firebase.auth.FirebaseUser
import com.puntogris.recap.data.repo.login.LoginRepository
import com.puntogris.recap.data.repo.recap.RecapRepository
import com.puntogris.recap.data.repo.user.UserRepository
import com.puntogris.recap.utils.Event
import com.puntogris.recap.utils.RecapOrder
import com.puntogris.recap.utils.ReviewOrder
import com.puntogris.recap.utils.SimpleResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recapRepository: RecapRepository,
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _usernameLiveData = MutableLiveData<String?>()
    val usernameLiveData: LiveData<String?> = _usernameLiveData

    private val _emailLiveData = MutableLiveData<String?>()
    val emailLiveData: LiveData<String?> = _emailLiveData

    private val _profilePictureLiveData = MutableLiveData<String?>()
    val profilePictureLiveData: LiveData<String?> = _profilePictureLiveData

    private val _authorizedLiveData = MutableLiveData(userRepository.isUserLoggedIn())
    val authorizedLiveData: LiveData<Boolean> = _authorizedLiveData

    private val _recapOrder = MutableStateFlow(RecapOrder.LATEST)
    val recapOrder: StateFlow<RecapOrder> = _recapOrder

    private val _reviewOrder = MutableStateFlow(ReviewOrder.ALL)
    val reviewOrder: StateFlow<ReviewOrder> = _reviewOrder

    val recapsFlow = _recapOrder.flatMapLatest {
        recapRepository.getRecapsPagingData(it)
    }.cachedIn(viewModelScope)

    val reviewsFlow = _reviewOrder.flatMapLatest {
        recapRepository.getReviewsPagingData(it)
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

    private fun updateUser(username: String?, email: String?, profilePicture: String?) {
        _usernameLiveData.postValue(username)
        _emailLiveData.postValue(email)
        _profilePictureLiveData.postValue(profilePicture)
    }

    fun refreshUserProfile() {
        val currentUser = userRepository.getFirebaseUser()
        if (currentUser != null) {
            _authorizedLiveData.postValue(true)
            updateUser(currentUser.displayName, currentUser.email, currentUser.photoUrl.toString())
        }
    }

    suspend fun logOut(): SimpleResult {
        _authorizedLiveData.value = false
        updateUser(null, null, null)
        return loginRepository.signOutUserFromFirebaseAndGoogle()
    }
}