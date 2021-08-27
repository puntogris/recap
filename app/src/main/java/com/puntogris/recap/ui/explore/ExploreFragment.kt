package com.puntogris.recap.ui.explore

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.explore.recaps.ExploreRecapFragment
import com.puntogris.recap.ui.explore.reviews.ExploreReviewFragment
import com.puntogris.recap.ui.main.MainViewModel
import com.puntogris.recap.ui.recap.RecapFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(R.layout.fragment_explore) {

    private val viewModel: ExploreViewModel by viewModels()
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        setupBottomAppBar()
        setupExplorePager()
    }

    private fun setupBottomAppBar(){
        binding.bottomAppBar.apply {
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_search -> {
                        findNavController().navigate(R.id.searchFragment)
                        true
                    }
                    else -> true
                }
            }
            setNavigationOnClickListener {
                findNavController().navigate(R.id.mainBottomNavigationDrawer)
            }
        }
    }

    private fun setupExplorePager(){
        binding.viewPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Explorar"
                else -> "Calificar"
            }
        }
        mediator?.attach()
    }

    private inner class ScreenSlidePagerAdapter(@NonNull parentFragment: FragmentManager) : FragmentStateAdapter(parentFragment, viewLifecycleOwner.lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment =
            (if (position == 0 ) ExploreRecapFragment() else ExploreReviewFragment()).apply {

            }
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }


}