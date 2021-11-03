package com.puntogris.recap.domain.repository

import com.puntogris.recap.model.Report
import com.puntogris.recap.utils.SimpleResult

interface ReportRepository {
    suspend fun sendRecapReport(report: Report): SimpleResult
}