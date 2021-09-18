package com.puntogris.recap.ui.search

import androidx.lifecycle.*
import com.puntogris.recap.data.repo.recap.RecapRepository
import com.puntogris.recap.models.Recap
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recapRepository: RecapRepository
): ViewModel() {

    private val _queryLiveData = MutableLiveData("")
    val queryLiveData: LiveData<String> = _queryLiveData


    val recapsLiveData = Transformations.map(queryLiveData) {
        if (it.isNotBlank()) {
            recapRepository.searchRecaps(it)
        } else {
            null
        }
    }

//    val usersLiveData = Transformations.switchMap(queryLiveData) {
//
//    }


    fun updateQuery(query: String) {
        _queryLiveData.postValue(query)
     //   _queryPhotoLiveData.postValue(query)
    }
}