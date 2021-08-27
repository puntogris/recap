package com.puntogris.recap.ui.search

import com.puntogris.recap.R
import com.puntogris.recap.databinding.BottomSheetSearchRecapFilterBinding
import com.puntogris.recap.ui.base.BaseBottomSheetFragment

class SearchRecapFilterBottomSheet: BaseBottomSheetFragment<BottomSheetSearchRecapFilterBinding>(R.layout.bottom_sheet_search_recap_filter) {

    override fun initializeViews() {
        binding.bottomSheet = this
    }
}