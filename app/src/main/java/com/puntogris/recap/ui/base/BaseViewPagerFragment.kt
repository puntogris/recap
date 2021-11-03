package com.puntogris.recap.ui.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

abstract class BaseViewPagerFragment<T: ViewDataBinding>(@LayoutRes override val layout: Int):
    BaseBindingFragment<T>(layout),
    PagerConfiguration,
    TabLayout.OnTabSelectedListener
{
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        viewPager.adapter = adapter

        tabLayout.addOnTabSelectedListener(this)

        mediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabsNames[position]
        }
        mediator?.attach()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {
        tab?.position?.let {
            viewModel.updateReselectedTabId(adapter.getItemId(it).toInt())
        }
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        viewPager.adapter = null
        super.onDestroyView()
    }
}