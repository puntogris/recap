package com.puntogris.recap.feature_recap.presentation.report_recap

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.R
import com.puntogris.recap.core.utils.*
import com.puntogris.recap.core.utils.Constants.INAPPROPRIATE
import com.puntogris.recap.core.utils.Constants.MISINFORMATION
import com.puntogris.recap.databinding.DialogReportRecapBinding
import com.puntogris.recap.feature_recap.domain.model.Report
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReportRecapDialog : DialogFragment() {

    private val binding: DialogReportRecapBinding by viewBinding(CreateMethod.INFLATE)

    private val viewModel: ReportViewModel by viewModels()

    private val args: ReportRecapDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding.dialog = this

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setTitle(R.string.report_recap_title)
            .create().also {
                it.setCanceledOnTouchOutside(false)
            }
    }

    fun onSendReportClicked() {
        val checked = binding.ratingOptions.checkedRadioButtonId

        if (checked == -1) {
            showSnackBar(getString(R.string.snack_option_required))
            return
        }
        val reason = if (checked == R.id.inappropriate_option) INAPPROPRIATE else MISINFORMATION

        showRatingInProgressUi()
        sendRecapReport(reason)
    }

    private fun sendRecapReport(reason: String) {
        lifecycleScope.launch {
            val result = viewModel.sendReport(Report(recapId = args.recapId, reason = reason))

            val (animation, summary) = when (result) {
                is SimpleResource.Error -> R.raw.error to R.string.general_connection_error_message
                SimpleResource.Success -> R.raw.success to R.string.send_report_success
            }

            with(binding) {
                animationView.playAnimationOnce(animation)
                ratingSummary.setText(summary)
                negativeButton.setText(R.string.action_close)
                positiveButton.gone()
            }
        }
    }

    private fun showRatingInProgressUi() {
        with(binding) {
            ratingOptions.gone()
            positiveButton.isEnabled = false
            animationView.visible()
            ratingSummary.setText(R.string.send_report_progress)
        }
    }
}