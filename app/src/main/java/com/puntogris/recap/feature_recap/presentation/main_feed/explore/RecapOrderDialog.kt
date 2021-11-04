package com.puntogris.recap.feature_recap.presentation.main_feed.explore

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.feature_recap.presentation.main_feed.HomeViewModel
import com.puntogris.recap.core.utils.RecapOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecapOrderDialog: DialogFragment() {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val orderOptions = enumValues<RecapOrder>()
            .map { getString(it.titleRes) }.toTypedArray()
        val currentSelection = viewModel.recapOrder.value!!.ordinal

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Ordenar por")
            .setSingleChoiceItems(orderOptions, currentSelection) { dialog, which ->
                if (which != currentSelection) viewModel.orderRecapsBy(which)
                dialog.dismiss()
            }
            .create()
    }
}