package com.puntogris.recap.core.data.repository

import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.domain.RatingRepository
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RatingRepositoryImpl(private val firebase: FirebaseClients) : RatingRepository {

    override suspend fun sendRecapRating(review: Review): SimpleResource =
        withContext(Dispatchers.IO) {
            SimpleResource.build {
                val ref = firebase.firestore.collection("reviews").document()
                review.id = ref.id
                review.reviewerId = firebase.currentUid()!!
            }
        }

    override suspend fun checkIfUserRatedRecap(recapId: String): Resource<Review> =
        withContext(Dispatchers.IO) {
            Resource.build {
                firebase.firestore.collection("reviews").document().get().await()
                    .toObject(Review::class.java)!!
            }
        }
}