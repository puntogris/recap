package com.puntogris.recap.feature_recap.domain.use_case

import com.puntogris.recap.core.utils.SimpleResult
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository

class SaveRecapDraftUseCase(
    private val repository: RecapRepository
) {
    suspend operator fun invoke(recap: Recap): SimpleResult {
        return repository.saveRecapDraft(recap)
    }
}