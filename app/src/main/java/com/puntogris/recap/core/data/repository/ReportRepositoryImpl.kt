package com.puntogris.recap.core.data.repository

import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.core.domain.ReportRepository
import com.puntogris.recap.Report
import com.puntogris.recap.core.utils.SimpleResult
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