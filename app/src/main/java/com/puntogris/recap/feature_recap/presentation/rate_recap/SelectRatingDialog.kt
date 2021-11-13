package com.puntogris.recap.feature_recap.presentation.rate_recap

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.R
import com.puntogris.recap.core.utils.*
import com.puntogris.recap.databinding.SelectRatingDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectRatingDialog : DialogFragment() {

    private lateinit var binding: SelectRatingDialogBinding
    private val viewModel: RateRecapViewModel by viewModels()
    private val args: SelectRatingDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = SelectRatingDialogBinding.inflate(layoutInflater)
        binding.dialog = this

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .setTitle(R.string.rate_recap_title)
            .create().also {
                it.setCanceledOnTouchOutside(false)
            }
    }

    fun onSendRecapClicked() {
        val checked = binding.ratingOptions.checkedRadioButtonId

        if (checked == -1) {
            showSnackBar(getString(R.string.snack_option_required))
            return
        }

        val score = if (checked == R.id.approve_option) {
            Constants.RATING_APPROVE
        } else {
            Constants.RATING_REJECT
        }

        showRatingInProgressUi()
        sendRecapRating(score)
    }

    private fun sendRecapRating(score: Int) {
        lifecycleScope.launch {
            val result = viewModel.sendRating(args.recapId, score)

            val (animation, summary) = when (result) {
                is SimpleResource.Error -> R.raw.error to R.string.send_rating_error
                SimpleResource.Success -> R.raw.success to R.string.send_rating_success
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
            ratingSummary.setText(R.string.send_rating_progress)
        }
    }
}