package com.puntogris.recap.ui.search.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.databinding.PublicProfileVhBinding
import com.puntogris.recap.model.PublicProfile

class PublicProfileViewHolder(private val binding: PublicProfileVhBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(profile: PublicProfile, shortClickListener: (PublicProfile) -> Unit){
        binding.apply {
            this.profile = profile
            root.setOnClickListener {
                shortClickListener(profile)
            }
            executePendingBindings()
        }
    }

    companion object{
        fun from(parent: ViewGroup): PublicProfileViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = PublicProfileVhBinding.inflate(layoutInflater, parent, false)
            return PublicProfileViewHolder(binding)
        }
    }
}