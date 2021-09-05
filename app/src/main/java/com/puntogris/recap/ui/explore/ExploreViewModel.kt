package com.puntogris.recap.ui.explore

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.puntogris.recap.data.repo.recap.RecapRepository
import com.puntogris.recap.utils.RecapOrder
import com.puntogris.recap.utils.ReviewOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val recapRepository: RecapRepository
): ViewModel() {

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
}