package com.puntogris.recap.data.repo.draft

import com.puntogris.recap.model.Recap
import com.puntogris.recap.utils.SimpleResult

interface IDraftRepository {
    suspend fun saveRecapLocalDb(recap: Recap): SimpleResult
}