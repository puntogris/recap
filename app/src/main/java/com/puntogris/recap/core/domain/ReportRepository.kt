package com.puntogris.recap.core.domain

import com.puntogris.recap.Report
import com.puntogris.recap.core.utils.SimpleResult

interface ReportRepository {
    suspend fun sendRecapReport(report: Report): SimpleResult
}