package com.puntogris.recap.feature_recap.domain.use_case

import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository

class SaveRecapDraftUseCase(
    private val repository: RecapRepository
) {
    suspend operator fun invoke(recap: Recap): SimpleResource {
        return repository.saveRecapDraft(recap)
    }
}