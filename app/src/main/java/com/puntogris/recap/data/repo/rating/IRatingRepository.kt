package com.puntogris.recap.data.repo.rating

import com.puntogris.recap.model.Review
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.SimpleResult

interface IRatingRepository {
    suspend fun sendRecapRating(review: Review): SimpleResult
    suspend fun checkIfUserRatedRecap(recapId: String): Result<Review>
}