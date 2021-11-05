package com.puntogris.recap.feature_search.presentation.user

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.puntogris.recap.core.utils.diff.PublicProfileDiffCallBack
import com.puntogris.recap.feature_profile.domain.model.PublicProfile

class PublicProfileAdapter(
    private val shortClickListener: (PublicProfile) -> Unit
) : PagingDataAdapter<PublicProfile, PublicProfileViewHolder>(PublicProfileDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicProfileViewHolder {
        return PublicProfileViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PublicProfileViewHolder, position: Int) {
        holder.bind(getItem(position)!!, shortClickListener)
    }
}
