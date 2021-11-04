package com.puntogris.recap.feature_recap.presentation.main_feed

import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.puntogris.recap.feature_recap.presentation.main_feed.explore.ExploreRecapFragment
import com.puntogris.recap.feature_recap.presentation.main_feed.reviews.ExploreReviewFragment

class HomeSlidePagerAdapter(@NonNull parentFragment: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(parentFragment, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int) =
        if (position == 0 ) ExploreRecapFragment() else ExploreReviewFragment()
}