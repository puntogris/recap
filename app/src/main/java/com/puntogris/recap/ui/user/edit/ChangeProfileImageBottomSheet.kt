package com.puntogris.recap.ui.user.edit

import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.puntogris.recap.R
import com.puntogris.recap.databinding.BottomSheetChangeProfileImageBinding
import com.puntogris.recap.ui.base.BaseBottomSheetFragment
import com.puntogris.recap.utils.EditPhotoOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeProfileImageBottomSheet:
    BaseBottomSheetFragment<BottomSheetChangeProfileImageBinding>(R.layout.bottom_sheet_change_profile_image) {

    override fun initializeViews() {
        binding.bottomSheet = this
        setupReportsAdapter()
    }

    private fun setupReportsAdapter(){
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

    private fun onOptionClicked(position: Int, options: Array<EditPhotoOptions>){
        options.elementAtOrNull(position)?.let {
            setFragmentResult("photoOption", bundleOf("data" to it))
            dismiss()
        }
    }
}