package com.puntogris.recap.ui.home

import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentHomeBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.explore.ExploreRecapFragment
import com.puntogris.recap.ui.home.reviews.ExploreReviewFragment
import com.puntogris.recap.ui.main.UiListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), UiListener {

    private val viewModel: HomeViewModel by activityViewModels()
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        binding.fragment = this
        setupBottomAppBar()
        setupExplorePager()

        setFragmentResultListener("data"){requestKey, bundle ->
            if (bundle.containsKey("result"))
            if (bundle.getBoolean("result")) showSnackBar("Cuenta cerrada correctamente.")
        }
    }

    private fun setupBottomAppBar(){
        binding.bottomAppBar.apply {
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_search -> {
                        findNavController().navigate(R.id.searchFragment)
                        true
                    }
                    R.id.action_order -> {
                        when (binding.tabLayout.selectedTabPosition) {
                            0 -> findNavController().navigate(R.id.recapOrderDialog)
                            1 -> findNavController().navigate(R.id.reviewOrderDialog)
                        }
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

    fun navigateToCreateRecap(){
        findNavController().navigate(R.id.createRecapFragment)
    }

    fun showFavoriteSnack(){
        showSnackBar("Agregado a favoritos", anchorView = binding.createFab)
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }

    override fun showSnackBar(
        message: String,
        duration: Int,
        actionText: Int,
        anchorView: View?,
        actionListener: View.OnClickListener?
    ) {
        Snackbar.make(binding.root, message, duration).apply {
            this.anchorView = binding.createFab
            if (actionListener != null) setAction(actionText, actionListener)
            show()
        }
    }
}