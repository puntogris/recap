package com.puntogris.recap.feature_recap.presentation.rate_recap

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.databinding.SelectRatingDialogBinding
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.core.utils.SimpleResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectRatingDialog: DialogFragment() {

    private lateinit var binding: SelectRatingDialogBinding
    private val viewModel: RateRecapViewModel by viewModels()
    private val  args: SelectRatingDialogArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = SelectRatingDialogBinding.inflate(layoutInflater)

        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }

    private fun rateRecap(){
        lifecycleScope.launch {
            when(viewModel.sendRating(Recap())){
                SimpleResult.Failure -> {}
                SimpleResult.Success -> {}
            }
        }
    }
}