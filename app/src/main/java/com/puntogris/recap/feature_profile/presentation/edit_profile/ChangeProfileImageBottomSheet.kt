package com.puntogris.recap.feature_profile.presentation.edit_profile

import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingBottomSheetFragment
import com.puntogris.recap.core.utils.EditPhotoOptions
import com.puntogris.recap.databinding.BottomSheetChangeProfileImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeProfileImageBottomSheet :
    BaseBindingBottomSheetFragment<BottomSheetChangeProfileImageBinding>(R.layout.bottom_sheet_change_profile_image) {

    override fun initializeViews() {
        binding.bottomSheet = this
        setupReportsAdapter()
    }

    private fun setupReportsAdapter() {
        val options = EditPhotoOptions.values()

        with(binding.reportList) {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                options.map { it.optionRes }
            )
            setOnItemClickListener { _, _, i, _ ->
                onOptionClicked(i, options)
            }
        }
    }

    private fun onOptionClicked(position: Int, options: Array<EditPhotoOptions>) {
        options.elementAtOrNull(position)?.let {
            setFragmentResult("photoOption", bundleOf("data" to it))
            dismiss()
        }
    }
}