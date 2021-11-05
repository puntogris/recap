package com.puntogris.recap.core.utils.diff

import androidx.recyclerview.widget.DiffUtil
import com.puntogris.recap.feature_recap.domain.model.Recap

class RecapDiffCallBack : DiffUtil.ItemCallback<Recap>() {
    override fun areItemsTheSame(oldItem: Recap, newItem: Recap): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recap, newItem: Recap): Boolean {
        return oldItem == newItem
    }
}