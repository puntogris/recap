package com.puntogris.recap.data.repository

import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.domain.repository.RatingRepository
import com.puntogris.recap.model.Review
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RatingRepositoryImpl(
    private val firebase: FirebaseDataSource,
) : RatingRepository {

    override suspend fun sendRecapRating(review: Review): SimpleResult =
        withContext(Dispatchers.IO) {
            SimpleResult.build {
                val ref = firebase.firestore.collection("reviews").document()
                review.id = ref.id
                review.reviewerId = firebase.currentUid()!!
            }
        }

    override suspend fun checkIfUserRatedRecap(recapId: String): Result<Exception, Review> =
        withContext(Dispatchers.IO) {
            Result.build {
                firebase.firestore.collection("reviews").document().get().await()
                    .toObject(Review::class.java)!!
            }
        }
}