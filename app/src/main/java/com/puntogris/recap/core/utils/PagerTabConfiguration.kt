package com.puntogris.recap.core.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.core.presentation.base.BaseRvViewModel

interface PagerTabConfiguration {
    val adapter: PagingDataAdapter<*, *>
    val recyclerView: RecyclerView
    fun onAdapterLoadStateChanged(state: CombinedLoadStates)
    val viewModel: BaseRvViewModel
}
