package com.puntogris.recap.ui.base

import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.puntogris.recap.ui.home.explore.ExploreRecapFragment
import com.puntogris.recap.ui.home.reviews.ExploreReviewFragment

abstract class BaseViewPagerFragment<T: ViewDataBinding>(@LayoutRes override val layout: Int):
    BaseFragment<T>(layout),
    PagerConfiguration,
    TabLayout.OnTabSelectedListener
{
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        viewPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)

        tabLayout.addOnTabSelectedListener(this)

        mediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsNames[position]
        }
        mediator?.attach()
    }

    private inner class ScreenSlidePagerAdapter(@NonNull parentFragment: FragmentManager) :
        FragmentStateAdapter(parentFragment, viewLifecycleOwner.lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int) =
            if (position == 0 ) ExploreRecapFragment() else ExploreReviewFragment()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        viewPager.adapter = null
        super.onDestroyView()
    }
}