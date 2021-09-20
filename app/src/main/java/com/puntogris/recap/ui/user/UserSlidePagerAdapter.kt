package com.puntogris.recap.ui.user

import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class UserSlidePagerAdapter(@NonNull parentFragment: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(parentFragment, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int) =
        if (position == 0) UserRecapsFragment() else {
            UserReviewsFragment()
        }
}