package com.puntogris.recap.ui.recap

import androidx.lifecycle.ViewModel
import com.puntogris.recap.data.repo.recap.RecapRepository
import com.puntogris.recap.models.Report
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecapViewModel @Inject constructor(
    private val recapRepository: RecapRepository
): ViewModel() {

    fun isUserLoggedIn() = true


    suspend fun sendReport(report: Report) = recapRepository.sendRecapReport(report)
}