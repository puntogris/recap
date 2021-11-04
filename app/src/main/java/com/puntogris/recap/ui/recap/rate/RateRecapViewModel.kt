package com.puntogris.recap.ui.recap.rate

import androidx.lifecycle.ViewModel
import com.puntogris.recap.domain.repository.RatingRepository
import com.puntogris.recap.model.Recap
import com.puntogris.recap.model.Review
import com.puntogris.recap.utils.SimpleResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RateRecapViewModel @Inject constructor(private val ratingRepository: RatingRepository
): ViewModel() {

    suspend fun checkRatingData(recapId: String) = ratingRepository.checkIfUserRatedRecap(recapId)

    suspend fun sendRating(recap: Recap): SimpleResult{
        val review = Review(recapId = recap.id)
        return ratingRepository.sendRecapRating(Review())
    }

}