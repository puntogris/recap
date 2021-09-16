package com.puntogris.recap.ui.home.explore

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.puntogris.recap.diff.RecapDiffCallBack
import com.puntogris.recap.models.Recap

class ExploreAdapter(
    private val shortClickListener: (Recap) -> Unit,
    private val longClickListener: (Recap) -> Unit
): PagingDataAdapter<Recap, ExploreRecapViewHolder>(RecapDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreRecapViewHolder {
        return ExploreRecapViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExploreRecapViewHolder, position: Int) {
        holder.bind(getItem(position)!!, shortClickListener, longClickListener)
    }
}
