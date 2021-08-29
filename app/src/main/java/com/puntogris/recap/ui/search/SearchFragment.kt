package com.puntogris.recap.ui.search

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchBinding
import com.puntogris.recap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        binding.fragment = this
        setupExplorePager()
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun setupExplorePager(){
        binding.viewPager.apply {
            adapter = ScreenSlidePagerAdapter(childFragmentManager)
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){

                //also check if the query is empty to not show and filter nothing
                override fun onPageSelected(position: Int) {
                    binding.filterButton.setVisibility(position == 0)
                }
            })
        }

        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Recaps"
                else -> "Usuarios"
            }
        }
        mediator?.attach()
    }

    private inner class ScreenSlidePagerAdapter(@NonNull parentFragment: FragmentManager) : FragmentStateAdapter(parentFragment, viewLifecycleOwner.lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment =
            (if (position == 0 ) SearchRecapFragment() else SearchUserFragment()).apply {

            }
    }

    private fun ExtendedFloatingActionButton.setVisibility(visible: Boolean) {
        if (visible) show() else hide()
    }

    fun showFilterBottomSheet(){
        findNavController().navigate(R.id.searchRecapFilterBottomSheet)
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}