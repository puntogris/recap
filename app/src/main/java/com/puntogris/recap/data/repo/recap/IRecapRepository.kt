package com.puntogris.recap.data.repo.recap

import com.puntogris.recap.models.Recap
import com.puntogris.recap.utils.SimpleResult

interface IRecapRepository {
    suspend fun saveRecapIntoDb(recap: Recap): SimpleResult
}