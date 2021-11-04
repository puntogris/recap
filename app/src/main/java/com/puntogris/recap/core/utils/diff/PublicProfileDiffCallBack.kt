package com.puntogris.recap.core.utils.diff

import androidx.recyclerview.widget.DiffUtil
import com.puntogris.recap.feature_profile.domain.model.PublicProfile

class PublicProfileDiffCallBack: DiffUtil.ItemCallback<PublicProfile>() {
    override fun areItemsTheSame(oldItem: PublicProfile, newItem: PublicProfile): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: PublicProfile, newItem: PublicProfile): Boolean {
        return oldItem == newItem
    }
}