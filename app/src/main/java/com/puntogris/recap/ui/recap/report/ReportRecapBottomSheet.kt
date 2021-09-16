package com.puntogris.recap.ui.recap.report

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.databinding.BottomSheetReportRecapBinding
import com.puntogris.recap.models.Report
import com.puntogris.recap.ui.base.BaseBottomSheetFragment
import com.puntogris.recap.ui.recap.RecapViewModel
import com.puntogris.recap.utils.SimpleResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportRecapBottomSheet:
    BaseBottomSheetFragment<BottomSheetReportRecapBinding>(R.layout.bottom_sheet_report_recap) {

    private val viewModel: RecapViewModel by viewModels()
    private val args: ReportRecapBottomSheetArgs by navArgs()

    override fun initializeViews() {
        setupReportsAdapter()
    }

    private fun setupReportsAdapter(){
        val reportReasons = enumValues<Report.Reason>()

        binding.reportList.apply {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_single_choice,
                reportReasons.map { getString(it.value) }
            )
            setOnItemClickListener { _, _, position, _ ->
                val report = Report(
                    reason = reportReasons[position].name,
                    recapId = args.recapId
                )
                onSendReportClicked(report)
            }
        }
    }

    private fun onSendReportClicked(report: Report){
        lifecycleScope.launch {
            when(viewModel.sendReport(report)){
                SimpleResult.Failure -> {

                }
                SimpleResult.Success -> {

                }
            }
        }
    }
}