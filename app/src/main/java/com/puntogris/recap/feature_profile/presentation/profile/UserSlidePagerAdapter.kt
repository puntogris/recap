package com.puntogris.recap.feature_profile.presentation.profile

import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.puntogris.recap.feature_profile.presentation.profile.profile_drafts.UserDraftsFragment
import com.puntogris.recap.feature_profile.presentation.profile.profile_recaps.UserRecapsFragment
import com.puntogris.recap.feature_profile.presentation.profile.profile_reviews.UserReviewsFragment

class UserSlidePagerAdapter(
    @NonNull parentFragment: FragmentManager,
    lifecycle: Lifecycle,
    private val isCurrentUser: Boolean
) :
    FragmentStateAdapter(parentFragment, lifecycle) {

    override fun getItemCount(): Int = if (isCurrentUser) 3 else 2

    override fun createFragment(position: Int) = when (position) {
        0 -> UserRecapsFragment()
        1 -> UserReviewsFragment()
        else -> UserDraftsFragment()
    }
}