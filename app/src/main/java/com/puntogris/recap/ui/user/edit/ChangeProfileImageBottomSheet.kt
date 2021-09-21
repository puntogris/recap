package com.puntogris.recap.ui.user.edit

import android.widget.ArrayAdapter
import com.puntogris.recap.R
import com.puntogris.recap.databinding.BottomSheetChangeProfileImageBinding
import com.puntogris.recap.ui.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeProfileImageBottomSheet:
    BaseBottomSheetFragment<BottomSheetChangeProfileImageBinding>(R.layout.bottom_sheet_change_profile_image) {

    override fun initializeViews() {
        binding.bottomSheet = this
        setupReportsAdapter()
    }

    private fun setupReportsAdapter(){
        with(binding.reportList) {
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                listOf("Nueva foto de perfil", "Eliminar foto de perfil")
            )
            setOnItemClickListener { _, _, i, _ ->

            }
        }
    }
}