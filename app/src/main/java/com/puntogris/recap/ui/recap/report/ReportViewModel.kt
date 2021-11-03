package com.puntogris.recap.ui.recap.report

import androidx.lifecycle.ViewModel
import com.puntogris.recap.data.repository.ReportRepositoryImpl
import com.puntogris.recap.model.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepositoryImpl
): ViewModel() {

    suspend fun sendReport(report: Report) = reportRepository.sendRecapReport(report)

}