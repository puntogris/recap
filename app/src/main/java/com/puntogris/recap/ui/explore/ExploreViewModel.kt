package com.puntogris.recap.ui.explore

import androidx.lifecycle.*
import com.puntogris.recap.utils.RecapOrder
import com.puntogris.recap.utils.ReviewOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(): ViewModel() {

    private val _recapOrderLiveData = MutableLiveData(RecapOrder.LATEST)
    val recapOrderLiveData: LiveData<RecapOrder> = _recapOrderLiveData

    private val _reviewOrderLiveData = MutableLiveData(ReviewOrder.ALL)
    val reviewOrderLiveData: LiveData<ReviewOrder> = _reviewOrderLiveData

    private val recapsListing = Transformations.map(_recapOrderLiveData) {
       // photoRepository.getPhotos(it, viewModelScope)
    }

    private val reviewListing = Transformations.map(_reviewOrderLiveData) {
      //  collectionRepository.getCollections(it, viewModelScope)
    }

 //   fun refreshRecaps() = recapsListing.value?.refresh?.invoke()

 //   fun refreshReviews() = reviewListing.value?.refresh?.invoke()


    fun orderRecapsBy(selection: Int) {
        RecapOrder.values().getOrNull(selection)?.let {
            _recapOrderLiveData.postValue(it)
        }
    }

    fun orderReviewsBy(selection: Int) {
        ReviewOrder.values().getOrNull(selection)?.let {
            _reviewOrderLiveData.postValue(it)
        }
    }
}