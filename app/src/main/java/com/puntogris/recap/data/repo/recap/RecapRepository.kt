package com.puntogris.recap.data.repo.recap

import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.models.Recap
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecapRepository @Inject constructor(
    private val firebase: FirebaseDataSource
):IRecapRepository {

    override suspend fun saveRecapIntoDb(recap: Recap): SimpleResult  = withContext(Dispatchers.IO){
        try {
            val ref = firebase.firestore
                .collection("users")
                .document(firebase.currentUid()!!)
                .collection("recaps")
                .document()

            recap.id = ref.id
            ref.set(recap).await()

            SimpleResult.Success
        }catch (E:Exception){
            SimpleResult.Failure
        }
    }
}