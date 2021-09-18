package com.puntogris.recap.ui.search.user

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.puntogris.recap.diff.PublicProfileDiffCallBack
import com.puntogris.recap.models.PublicProfile

class PublicProfileAdapter(
    private val shortClickListener: (PublicProfile) -> Unit
): PagingDataAdapter<PublicProfile, PublicProfileViewHolder>(PublicProfileDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicProfileViewHolder {
        return PublicProfileViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PublicProfileViewHolder, position: Int) {
        holder.bind(getItem(position)!!, shortClickListener)
    }
}
