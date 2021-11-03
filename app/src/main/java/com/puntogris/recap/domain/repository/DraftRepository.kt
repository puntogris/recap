package com.puntogris.recap.domain.repository

import com.puntogris.recap.model.Recap
import com.puntogris.recap.utils.SimpleResult

interface DraftRepository {
    suspend fun saveRecapLocalDb(recap: Recap): SimpleResult
}