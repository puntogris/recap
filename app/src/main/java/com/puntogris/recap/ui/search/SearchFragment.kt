package com.puntogris.recap.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.explore.ExploreViewModel
import com.puntogris.recap.ui.explore.recaps.ExploreRecapFragment
import com.puntogris.recap.ui.explore.reviews.ExploreReviewFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        setupExplorePager()
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

    }

    private fun setupExplorePager(){
        binding.viewPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
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

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }


}