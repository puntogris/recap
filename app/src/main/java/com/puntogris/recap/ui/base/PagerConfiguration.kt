package com.puntogris.recap.ui.base

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

interface PagerConfiguration{
    val viewPager: ViewPager2
    val tabLayout: TabLayout
    val tabsNames: List<String>
}