package com.puntogris.recap.diff

import androidx.recyclerview.widget.DiffUtil
import com.puntogris.recap.models.Recap

class RecapDiffCallBack: DiffUtil.ItemCallback<Recap>() {
    override fun areItemsTheSame(oldItem: Recap, newItem: Recap): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recap, newItem: Recap): Boolean {
        return oldItem == newItem
    }
}