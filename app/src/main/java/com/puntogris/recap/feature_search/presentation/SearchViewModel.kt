package com.puntogris.recap.feature_search.presentation

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.puntogris.recap.core.presentation.base.BaseRvViewModel
import com.puntogris.recap.feature_search.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseRvViewModel() {

    private val _queryLiveData = MutableLiveData("")
    val queryLiveData: LiveData<String> = _queryLiveData

    val recapsLiveData = Transformations.switchMap(queryLiveData) {
        if (it.isNotBlank()) {
            searchRepository.searchRecaps(it).asLiveData()
        } else {
            MutableLiveData(PagingData.empty())
        }
    }.cachedIn(viewModelScope)

    val usersLiveData = Transformations.switchMap(queryLiveData) {
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