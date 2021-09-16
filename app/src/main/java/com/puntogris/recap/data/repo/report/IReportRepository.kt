package com.puntogris.recap.data.repo.report

import com.puntogris.recap.models.Report
import com.puntogris.recap.utils.SimpleResult

interface IReportRepository {
    suspend fun sendRecapReport(report: Report): SimpleResult
}