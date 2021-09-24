package com.puntogris.recap.data.repo.draft

import com.puntogris.recap.data.local.RecapDao
import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.model.Recap
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DraftRepository @Inject constructor(
    private val recapDao: RecapDao,
    private val firebase: FirebaseDataSource
): IDraftRepository{

    override suspend fun saveRecapLocalDb(recap: Recap) = withContext(Dispatchers.IO) {
        recap.id = firebase.firestore.collection("recaps").document().id
        if (recapDao.insert(recap) != 0L) SimpleResult.Success else SimpleResult.Failure
    }

}