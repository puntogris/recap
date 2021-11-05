package com.puntogris.recap.feature_recap.domain.use_case

import com.puntogris.recap.Report
import com.puntogris.recap.core.utils.SimpleResult
import com.puntogris.recap.feature_recap.domain.repository.RecapRepository

class ReportRecapUseCase(
    private val repository: RecapRepository
) {
    suspend operator fun invoke(report: Report): SimpleResult{
        return repository.reportRecap(report)
    }
}