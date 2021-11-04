package com.puntogris.recap.core.utils

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.puntogris.recap.core.presentation.base.BaseRvViewModel

interface PagerConfiguration {
    val viewPager: ViewPager2
    val tabLayout: TabLayout
    val tabsNames: Array<String>
    val viewModel: BaseRvViewModel
    val adapter: FragmentStateAdapter
}