package com.puntogris.recap.ui.search

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.puntogris.recap.data.repo.recap.RecapRepository
import com.puntogris.recap.data.repo.search.SearchRepository
import com.puntogris.recap.models.Recap
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

    private val _queryLiveData = MutableLiveData("")
    val queryLiveData: LiveData<String> = _queryLiveData

    val recapsLiveData = Transformations.switchMap(queryLiveData) {
        if (it.isNotBlank()) {
            searchRepository.searchRecaps(it).asLiveData()
        } else {
            null
        }
    }.cachedIn(viewModelScope)

    val usersLiveData = Transformations.switchMap(queryLiveData){
        if (it.isNotBlank()) {
            searchRepository.searchUsers(it).asLiveData()
        } else {
            null
        }
    }.cachedIn(viewModelScope)

    fun updateQuery(query: String) {
        _queryLiveData.postValue(query)
    }
}