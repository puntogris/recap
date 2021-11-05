package com.puntogris.recap.feature_recap.domain.use_case

import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.feature_recap.domain.model.Report
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository

class ReportRecapUseCase(
    private val repository: RecapRepository
) {
    suspend operator fun invoke(report: Report): SimpleResource {
        return repository.reportRecap(report)
    }
}