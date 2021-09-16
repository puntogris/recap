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
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.explore.ExploreRecapFragment
import com.puntogris.recap.ui.home.explore.RecapOrderDialog
import com.puntogris.recap.ui.home.reviews.ExploreReviewFragment
import com.puntogris.recap.ui.home.reviews.ReviewOrderDialog
import com.puntogris.recap.ui.main.UiListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), UiListener {

    private val viewModel: HomeViewModel by viewModels()
    private var mediator: TabLayoutMediator? = null

    private val bottomNavDrawer: HomeBottomNavigationDrawer by lazy(LazyThreadSafetyMode.NONE) {
        HomeBottomNavigationDrawer.newInstance()
    }

    override fun initializeViews() {
        binding.fragment = this
        setupBottomAppBar()
        setupExplorePager()

        setFragmentResultListener("home_fragment"){_, bundle ->
            if (bundle.containsKey("log_out_result") && bundle.getBoolean("log_out_result"))
                showSnackBar("Cuenta cerrada correctamente.")
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
                            0 -> RecapOrderDialog().show(childFragmentManager, "recap_order_dialog")
                            1 -> ReviewOrderDialog().show(childFragmentManager, "review_order_dialog")
                        }
                        true
                    }
                    else -> true
                }
            }
            setNavigationOnClickListener {
                showBottomDrawer()
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

        override fun createFragment(position: Int) =
            if (position == 0 ) ExploreRecapFragment() else ExploreReviewFragment()
    }

    fun navigateToCreateRecap(){
        findNavController().navigate(R.id.createRecapFragment)
    }

    fun navigateToRecap(recap: Recap){
        val action = HomeFragmentDirections.actionHomeFragmentToRecapFragment(recap)
        findNavController().navigate(action)
    }

    fun showFavoriteSnack(){
        showSnackBar("Agregado a favoritos", anchorView = binding.createFab)
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

    private fun showBottomDrawer() {
        bottomNavDrawer.show(childFragmentManager, HomeBottomNavigationDrawer.TAG)
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshUserProfile()
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}