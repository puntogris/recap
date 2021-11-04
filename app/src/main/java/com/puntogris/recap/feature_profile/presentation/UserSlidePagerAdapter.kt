package com.puntogris.recap.feature_profile.presentation

import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.puntogris.recap.presentation.user.draft.UserDraftsFragment

class UserSlidePagerAdapter(@NonNull parentFragment: FragmentManager, lifecycle: Lifecycle, private val isCurrentUser: Boolean) :
    FragmentStateAdapter(parentFragment, lifecycle) {

    override fun getItemCount(): Int = if (isCurrentUser) 3 else 2

    override fun createFragment(position: Int) = when (position) {
        0 -> UserRecapsFragment()
        1 -> UserReviewsFragment()
        else -> UserDraftsFragment()
    }
}