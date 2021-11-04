package com.puntogris.recap.data.repository

import com.puntogris.recap.data.remote.FirebaseClients
import com.puntogris.recap.domain.repository.ReportRepository
import com.puntogris.recap.model.Report
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReportRepositoryImpl(private val firebase: FirebaseClients) : ReportRepository {

    override suspend fun sendRecapReport(report: Report): SimpleResult =
        withContext(Dispatchers.IO) {
            SimpleResult.build {
                report.uid = firebase.currentUid()!!
                firebase.firestore.collection("reports").add(report)
            }
        }
}