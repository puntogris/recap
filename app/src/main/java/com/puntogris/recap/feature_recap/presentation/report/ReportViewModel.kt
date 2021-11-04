package com.puntogris.recap.feature_recap.presentation.report

import androidx.lifecycle.ViewModel
import com.puntogris.recap.core.domain.ReportRepository
import com.puntogris.recap.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
): ViewModel() {

    suspend fun sendReport(report: Report) = reportRepository.sendRecapReport(report)

}