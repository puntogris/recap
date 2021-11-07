package com.puntogris.recap.feature_recap.presentation.rate_recap

import androidx.lifecycle.ViewModel
import com.puntogris.recap.core.domain.RatingRepository
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RateRecapViewModel @Inject constructor(
    private val ratingRepository: RatingRepository
) : ViewModel() {

    suspend fun checkRatingData(recapId: String) = ratingRepository.checkIfUserRatedRecap(recapId)
    //TODO instead of storing the recaps a user rated in a collection inside that user
    // what if we save inside the recap the id of what people rated it, and check if the current user
    // also we could include this in the query so a user doesnt get a recap that already reviewd
    // and like a recap wont be reviewd more than 10 or a low number
    // then what we could do if a recap has lets say 10 votes, if the majority is positive
    // approved, and idk if this is possible maybe there is a way to get recaps where the reviewrsId list
    // size isnt bigger that 10

    suspend fun sendRating(recap: Recap): SimpleResource {
        val review = Review(recapId = recap.id)
        return ratingRepository.sendRecapRating(Review())
    }

}