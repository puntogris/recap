package com.puntogris.recap.feature_recap.presentation.rate_recap

import androidx.lifecycle.ViewModel
import com.puntogris.recap.core.domain.RatingRepository
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.Review
import com.puntogris.recap.core.utils.SimpleResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RateRecapViewModel @Inject constructor(
    private val ratingRepository: RatingRepository
) : ViewModel() {

    suspend fun checkRatingData(recapId: String) = ratingRepository.checkIfUserRatedRecap(recapId)

    suspend fun sendRating(recap: Recap): SimpleResult {
        val review = Review(recapId = recap.id)
        return ratingRepository.sendRecapRating(Review())
    }

}