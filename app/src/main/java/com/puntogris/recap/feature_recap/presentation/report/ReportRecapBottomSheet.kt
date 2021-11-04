package com.puntogris.recap.feature_recap.presentation.report

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.databinding.BottomSheetReportRecapBinding
import com.puntogris.recap.Report
import com.puntogris.recap.core.presentation.base.BaseBindingBottomSheetFragment
import com.puntogris.recap.core.utils.SimpleResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportRecapBottomSheet:
    BaseBindingBottomSheetFragment<BottomSheetReportRecapBinding>(R.layout.bottom_sheet_report_recap) {

    private val viewModel: ReportViewModel by viewModels()
    private val args: ReportRecapBottomSheetArgs by navArgs()
    private val reportReasons = enumValues<Report.Reason>()

    override fun initializeViews() {
        binding.bottomSheet = this
        setupReportsAdapter()
    }

    private fun setupReportsAdapter(){
        binding.reportList.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_single_choice,
            reportReasons.map { getString(it.value) }
        )
    }

    fun onSendReportClicked(){
        val reason = reportReasons[binding.reportList.checkedItemPosition].name
        val report = Report(
            reason = reason,
            recapId = args.recapId
        )

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