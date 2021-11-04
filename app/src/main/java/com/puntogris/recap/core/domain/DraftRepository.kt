package com.puntogris.recap.core.domain

import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.core.utils.SimpleResult

interface DraftRepository {
    suspend fun saveRecapLocalDb(recap: Recap): SimpleResult
}