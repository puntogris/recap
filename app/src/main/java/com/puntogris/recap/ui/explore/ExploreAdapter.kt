package com.puntogris.recap.ui.explore

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import com.puntogris.recap.diff.RecapDiffCallBack
import com.puntogris.recap.models.Recap

class ExploreAdapter(
    private val shortClickListener: (Recap) -> Unit,
    private val longClickListener: (Recap) -> Unit
): PagingDataAdapter<Recap, ExploreViewHolder>(RecapDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        return ExploreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        holder.bind(getItem(position)!!, shortClickListener, longClickListener)
    }
}