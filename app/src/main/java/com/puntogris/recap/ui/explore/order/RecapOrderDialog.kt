package com.puntogris.recap.ui.explore.order

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.ui.explore.ExploreViewModel
import com.puntogris.recap.utils.RecapOrder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecapOrderDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val orderOptions = enumValues<RecapOrder>()
            .map { getString(it.titleRes) }.toTypedArray()
     //   val currentSelection = viewModel.recapOrderLiveData.value?.ordinal ?: 0


        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Ordenar por")
            .setSingleChoiceItems(orderOptions, 1) { dialog, which ->
           //     if (which != currentSelection) viewModel.orderRecapsBy(which)
                dialog.dismiss()
                setFragmentResult("data", bundleOf("order" to which))
            }
            .create()
    }
}