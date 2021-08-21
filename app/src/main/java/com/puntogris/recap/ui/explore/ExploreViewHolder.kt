package com.puntogris.recap.ui.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.databinding.ExploreApprovedRecapBinding
import com.puntogris.recap.models.Recap

class ExploreViewHolder(private val binding: ExploreApprovedRecapBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(recap: Recap, shortClickListener: (Recap) -> Unit, longClickListener: (Recap) -> Unit){
        binding.apply {
            this.recap = recap
            root.setOnClickListener {
                shortClickListener(recap)
            }
            root.setOnLongClickListener {
                longClickListener(recap)
                true
            }
            executePendingBindings()
        }
    }

    companion object{
        fun from(parent: ViewGroup): ExploreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ExploreApprovedRecapBinding.inflate(layoutInflater, parent, false)
            return ExploreViewHolder(binding)
        }
    }
}