package com.puntogris.recap.core.domain

import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.model.Review

interface RatingRepository {
    suspend fun sendRecapRating(review: Review): SimpleResource
    suspend fun checkIfUserRatedRecap(recapId: String): Resource<Review>
}