package com.puntogris.recap.domain.repository

import com.puntogris.recap.model.Review
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.SimpleResult

interface RatingRepository {
    suspend fun sendRecapRating(review: Review): SimpleResult
    suspend fun checkIfUserRatedRecap(recapId: String): Result<Exception, Review>
}