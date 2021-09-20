package com.puntogris.recap.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.puntogris.recap.data.repo.search.SearchRepository
import com.puntogris.recap.ui.base.BaseRvViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): BaseRvViewModel() {

    private val _queryLiveData = MutableLiveData("")
    val queryLiveData: LiveData<String> = _queryLiveData

    val recapsLiveData = Transformations.switchMap(queryLiveData) {
        if (it.isNotBlank()) {
            searchRepository.searchRecaps(it).asLiveData()
        } else {
            MutableLiveData(PagingData.empty())
        }
    }.cachedIn(viewModelScope)

    val usersLiveData = Transformations.switchMap(queryLiveData){
        if (it.isNotBlank()) {
            searchRepository.searchUsers(it).asLiveData()
        } else {
            MutableLiveData(PagingData.empty())
        }
    }.cachedIn(viewModelScope)

    fun updateQuery(query: String) {
        _queryLiveData.value = query
    }
}