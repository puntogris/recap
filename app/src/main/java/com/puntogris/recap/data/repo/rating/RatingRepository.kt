package com.puntogris.recap.data.repo.rating

import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.model.Review
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatingRepository @Inject constructor(
    private val firebase: FirebaseDataSource,
    ) : IRatingRepository{

    override suspend fun sendRecapRating(review: Review): SimpleResult = withContext(Dispatchers.IO){
        try {
            val ref = firebase.firestore.collection("reviews").document()
            review.id = ref.id
            review.reviewerId = firebase.currentUid()!!

            SimpleResult.Success
        }catch (e: Exception){
            SimpleResult.Failure
        }
    }

    override suspend fun checkIfUserRatedRecap(recapId: String): Result<Review> = withContext(Dispatchers.IO) {
        try{
            firebase.firestore.collection("reviews").document().get()

            Result.Success(Review())
        }catch(e: Exception){
            Result.Error(e)
        }
    }
}