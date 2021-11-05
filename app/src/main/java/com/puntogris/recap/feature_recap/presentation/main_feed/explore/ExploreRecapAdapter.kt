package com.puntogris.recap.feature_recap.presentation.main_feed.explore

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.puntogris.recap.core.utils.diff.RecapDiffCallBack
import com.puntogris.recap.feature_recap.domain.model.Recap

class ExploreRecapAdapter(
    private val shortClickListener: (Recap) -> Unit,
    private val longClickListener: (Recap) -> Unit
) : PagingDataAdapter<Recap, ExploreRecapViewHolder>(RecapDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreRecapViewHolder {
        return ExploreRecapViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExploreRecapViewHolder, position: Int) {
        holder.bind(getItem(position)!!, shortClickListener, longClickListener)
    }
}
