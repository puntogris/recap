package com.puntogris.recap.ui.base

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

interface PagerTabConfiguration {
    val adapter: PagingDataAdapter<*, *>
    val recyclerView: RecyclerView
    fun onAdapterLoadStateChanged(state: CombinedLoadStates)
    val viewModel: BaseRvViewModel
}
