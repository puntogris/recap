package com.puntogris.recap.presentation.user.draft

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.puntogris.recap.core.utils.diff.RecapDiffCallBack
import com.puntogris.recap.feature_recap.domain.model.Recap

class UserDraftsAdapter(private val clickListener: (Recap) -> Unit) :
    PagingDataAdapter<Recap, UserDraftViewHolder>(RecapDiffCallBack()) {

    override fun onBindViewHolder(holder: UserDraftViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDraftViewHolder {
        return UserDraftViewHolder.from(parent)
    }
}