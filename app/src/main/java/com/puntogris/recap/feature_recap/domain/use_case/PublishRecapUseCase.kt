package com.puntogris.recap.feature_recap.domain.use_case

import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapLink
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository

class PublishRecapUseCase(
    private val repository: RecapRepository
) {
    suspend operator fun invoke(recap: Recap): Resource<RecapLink> {
        return repository.publishRecap(recap)
    }
}