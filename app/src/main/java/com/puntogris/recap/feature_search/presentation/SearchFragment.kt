package com.puntogris.recap.feature_search.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.puntogris.recap.NavigationDirections
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchBinding
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.core.presentation.base.BaseViewPagerFragment
import com.puntogris.recap.core.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseViewPagerFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override val viewPager: ViewPager2
        get() = binding.viewPager

    override val tabLayout: TabLayout
        get() = binding.tabLayout

    override val adapter: FragmentStateAdapter
        get() = SearchSlidePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

    override val tabsNames: Array<String>
        get() = resources.getStringArray(R.array.search_tabs)

    override val viewModel: SearchViewModel by viewModels()

    override fun initializeViews() {
        super.initializeViews()
        binding.fragment = this
        registerToolbarBackButton(binding.toolbar)
        registerQueryTextListener()
        subscribeFilterButtonVisibility()
    }

    private fun registerQueryTextListener() {
        binding.searchTextInputLayout.editText?.apply {
            if (text.isNullOrBlank()) focusAndShowKeyboard()
            onImeActionSearch {
                viewModel.updateQuery(text.toString())
                hideKeyboard()
            }
        }
    }

    private fun subscribeFilterButtonVisibility() {
        viewModel.queryLiveData.observe(viewLifecycleOwner) {
            binding.filterButton.setVisibility(binding.viewPager.currentItem == 0 && it.isNotBlank())
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        binding.filterButton.setVisibility(
            tab?.position == 0 && !viewModel.queryLiveData.value.isNullOrBlank()
        )
    }

    fun showFilterBottomSheet() {
        findNavController().navigate(R.id.searchRecapFilterBottomSheet)
    }

    fun navigateToRecap(recap: Recap) {
        val action = SearchFragmentDirections.actionSearchFragmentToRecapFragment(recap)
        findNavController().navigate(action)
    }

    fun navigateToProfile(userId: String) {
        val action = NavigationDirections.actionGlobalUserFragment(userId = userId)
        findNavController().navigate(action)
    }

    fun navigateToProfile(publicProfile: PublicProfile) {
        val action = NavigationDirections.actionGlobalUserFragment(profile = publicProfile)
        findNavController().navigate(action)
    }

    fun showFavoriteSnack() {
        showSnackBar("Agregado a favoritos")
    }
}