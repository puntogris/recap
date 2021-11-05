package com.puntogris.recap.feature_search.presentation.recap

import android.widget.ArrayAdapter
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingBottomSheetFragment
import com.puntogris.recap.databinding.BottomSheetSearchRecapFilterBinding
import com.puntogris.recap.feature_search.presentation.RecapLanguages
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecapFilterBottomSheet :
    BaseBindingBottomSheetFragment<BottomSheetSearchRecapFilterBinding>(R.layout.bottom_sheet_search_recap_filter) {

    override fun initializeViews() {
        binding.bottomSheet = this

        val languages = RecapLanguages.values()
        val languagesNames = languages.map { getString(it.nameRes) }

        binding.languagesMenu.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                languagesNames
            )
        )

    }

    fun onApplyFilters() {

    }
}