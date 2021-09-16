package com.puntogris.recap.data.repo.report

import com.puntogris.recap.data.remote.FirebaseDataSource
import com.puntogris.recap.models.Report
import com.puntogris.recap.utils.SimpleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val firebase: FirebaseDataSource
):IReportRepository {

    override suspend fun sendRecapReport(report: Report): SimpleResult = withContext(Dispatchers.IO){
        try {
            report.uid = firebase.currentUid()!!
            firebase.firestore.collection("reports").add(report)
            SimpleResult.Success
        }catch (e:Exception){
            SimpleResult.Failure
        }
    }

}