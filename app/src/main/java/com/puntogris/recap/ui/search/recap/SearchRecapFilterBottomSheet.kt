package com.puntogris.recap.ui.search.recap

import android.widget.ArrayAdapter
import com.puntogris.recap.R
import com.puntogris.recap.databinding.BottomSheetSearchRecapFilterBinding
import com.puntogris.recap.ui.base.BaseBottomSheetFragment
import com.puntogris.recap.ui.search.RecapLanguages
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecapFilterBottomSheet :
    BaseBottomSheetFragment<BottomSheetSearchRecapFilterBinding>(R.layout.bottom_sheet_search_recap_filter) {

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