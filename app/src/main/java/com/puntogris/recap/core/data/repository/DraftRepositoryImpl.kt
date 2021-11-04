package com.puntogris.recap.core.data.repository

import com.puntogris.recap.core.data.local.RecapDao
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.domain.DraftRepository
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.core.utils.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DraftRepositoryImpl(
    private val recapDao: RecapDao,
    private val firebase: FirebaseClients
) : DraftRepository {

    override suspend fun saveRecapLocalDb(recap: Recap) = withContext(Dispatchers.IO) {
        recap.id = firebase.firestore.collection("recaps").document().id
        if (recapDao.insert(recap) != 0L) SimpleResult.Success else SimpleResult.Failure
    }

}