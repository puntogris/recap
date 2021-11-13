package com.puntogris.recap.feature_recap.presentation.main_feed

import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseViewPagerFragment
import com.puntogris.recap.core.utils.UiListener
import com.puntogris.recap.databinding.FragmentHomeBinding
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.presentation.main_feed.explore.RecapOrderDialog
import com.puntogris.recap.feature_recap.presentation.main_feed.reviews.ReviewOrderDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewPagerFragment<FragmentHomeBinding>(R.layout.fragment_home),
    UiListener {

    override val viewPager: ViewPager2
        get() = binding.viewPager

    override val tabLayout: TabLayout
        get() = binding.tabLayout

    override val tabsNames: Array<String>
        get() = resources.getStringArray(R.array.main_feed_tabs)

    override val viewModel: HomeViewModel by viewModels()

    override val adapter: FragmentStateAdapter
        get() = HomeSlidePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

    override fun initializeViews() {
        super.initializeViews()
        binding.fragment = this

        setupBottomAppBar()
        registerResultListener()
    }

    private fun setupBottomAppBar() {
        binding.bottomAppBar.apply {
            setOnMenuItemClickListener {
                handleMenuItemClick(it.itemId)
                true
            }
            setNavigationOnClickListener {
                showBottomDrawer()
            }
        }
    }

    private fun handleMenuItemClick(itemId: Int) {
        when (itemId) {
            R.id.action_search -> {
                findNavController().navigate(R.id.searchFragment)
            }
            R.id.action_order -> {
                when (binding.tabLayout.selectedTabPosition) {
                    0 -> RecapOrderDialog().show(childFragmentManager, "recap_order_dialog")
                    1 -> ReviewOrderDialog().show(childFragmentManager, "review_order_dialog")
                }
            }
        }
    }

    private fun registerResultListener() {
        setFragmentResultListener("home_fragment") { _, bundle ->
            if (bundle.containsKey("log_out_result") && bundle.getBoolean("log_out_result"))
                showSnackBar("Cuenta cerrada correctamente.")
        }
    }

    fun navigateToCreateRecap() {
        findNavController().navigate(R.id.createRecapGraph)
    }

    fun navigateToRecap(recap: Recap) {
        val action = HomeFragmentDirections.actionHomeFragmentToRecapFragment(recap)
        findNavController().navigate(action)
    }

    fun showFavoriteSnack() {
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
        HomeBottomNavigationDrawer
            .newInstance()
            .show(childFragmentManager, HomeBottomNavigationDrawer.TAG)
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshUserProfile()
    }
}