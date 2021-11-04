package com.puntogris.recap.core.domain

import com.puntogris.recap.feature_recap.domain.model.Review
import com.puntogris.recap.core.utils.Result
import com.puntogris.recap.core.utils.SimpleResult

interface RatingRepository {
    suspend fun sendRecapRating(review: Review): SimpleResult
    suspend fun checkIfUserRatedRecap(recapId: String): Result<Exception, Review>
}