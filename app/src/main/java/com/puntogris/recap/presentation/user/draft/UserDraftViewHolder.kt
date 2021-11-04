package com.puntogris.recap.presentation.user.draft

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.databinding.UserDraftVhBinding
import com.puntogris.recap.feature_recap.domain.model.Recap

class UserDraftViewHolder(private val binding: UserDraftVhBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(recap: Recap, clickListener: (Recap) -> Unit){
        binding.apply {
            this.recap = recap
            root.setOnClickListener {
                clickListener(recap)
            }
            executePendingBindings()
        }
    }

    companion object{
        fun from(parent: ViewGroup): UserDraftViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = UserDraftVhBinding.inflate(layoutInflater, parent, false)
            return UserDraftViewHolder(binding)
        }
    }
}
