package com.puntogris.recap.feature_recap.presentation.main_feed

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.puntogris.recap.core.domain.use_case.GetCurrentAuthUser
import com.puntogris.recap.core.domain.use_case.isLoggedIn
import com.puntogris.recap.core.presentation.base.BaseRvViewModel
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_auth.domain.user_case.LogoutUseCase
import com.puntogris.recap.feature_recap.domain.model.RecapOrder
import com.puntogris.recap.feature_recap.domain.model.ReviewOrder
import com.puntogris.recap.feature_recap.domain.use_case.GetRecapsUseCase
import com.puntogris.recap.feature_recap.domain.use_case.GetReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecapsUseCase: GetRecapsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getCurrentAuthUser: GetCurrentAuthUser,
    private val logoutUseCase: LogoutUseCase
) : BaseRvViewModel() {

    private val _userId = MutableLiveData<String?>()
    val userId: LiveData<String?> = _userId

    private val _usernameLiveData = MutableLiveData<String?>()
    val usernameLiveData: LiveData<String?> = _usernameLiveData

    private val _emailLiveData = MutableLiveData<String?>()
    val emailLiveData: LiveData<String?> = _emailLiveData

    private val _profilePictureLiveData = MutableLiveData<String?>()
    val profilePictureLiveData: LiveData<String?> = _profilePictureLiveData

    private val _authorizedLiveData = MutableLiveData(getCurrentAuthUser.isLoggedIn())
    val authorizedLiveData: LiveData<Boolean> = _authorizedLiveData

    private val _recapOrder = MutableLiveData(RecapOrder.LATEST)
    val recapOrder: LiveData<RecapOrder> = _recapOrder

    private val _reviewOrder = MutableLiveData(ReviewOrder.ALL)
    val reviewOrder: LiveData<ReviewOrder> = _reviewOrder

    val recapsLiveData = Transformations.switchMap(recapOrder) {
        getRecapsUseCase(it).asLiveData()
    }.cachedIn(viewModelScope)

    val reviewsLiveData = Transformations.switchMap(reviewOrder) {
        getReviewsUseCase(it).asLiveData()
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

    fun isUserLoggedIn() = getCurrentAuthUser.isLoggedIn()

    private fun updateUser(
        username: String?,
        email: String?,
        profilePicture: String?,
        uid: String?
    ) {
        _usernameLiveData.postValue(username)
        _emailLiveData.postValue(email)
        _profilePictureLiveData.postValue(profilePicture)
        _userId.postValue(uid)
    }

    fun refreshUserProfile() {
        getCurrentAuthUser()?.let {
            _authorizedLiveData.postValue(true)
            updateUser(
                it.displayName,
                it.email,
                it.photoUrl.toString(),
                it.uid
            )
        }
    }

    suspend fun logOut(): SimpleResource {
        _authorizedLiveData.value = false
        updateUser(null, null, null, null)
        return logoutUseCase()
    }
}