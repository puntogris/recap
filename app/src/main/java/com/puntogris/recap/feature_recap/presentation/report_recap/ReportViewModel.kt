package com.puntogris.recap.feature_recap.presentation.report_recap

import androidx.lifecycle.ViewModel
import com.puntogris.recap.Report
import com.puntogris.recap.feature_recap.domain.use_case.ReportRecapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRecapUseCase: ReportRecapUseCase
): ViewModel() {

    suspend fun sendReport(report: Report) = reportRecapUseCase(report)

}