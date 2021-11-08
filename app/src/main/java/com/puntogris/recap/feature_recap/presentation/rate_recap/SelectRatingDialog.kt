package com.puntogris.recap.feature_recap.presentation.rate_recap

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.R
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.core.utils.gone
import com.puntogris.recap.core.utils.playAnimationOnce
import com.puntogris.recap.core.utils.visible
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
            .setTitle("Rate recap")
            .setMessage("Ten en cuenta que esto es tomado seriamente y puedes ser castigado por esto.")
            .create().also {
                it.setCanceledOnTouchOutside(false)
            }
    }

    fun rateRecap() {
        val score = 1
        binding.radioGroup.gone()
        binding.button5.isEnabled = false
        binding.animationView.visible()

        lifecycleScope.launch {
            when (viewModel.sendRating(args.recapId, score)) {
                is SimpleResource.Error -> {
                    binding.animationView.playAnimationOnce(R.raw.error)
                }
                SimpleResource.Success -> {
                    binding.animationView.playAnimationOnce(R.raw.success)
                }
            }

            binding.button5.gone()
            binding.button4.text = "cerrar"
        }
    }


}