package com.puntogris.recap.ui.search

import android.view.inputmethod.EditorInfo
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.search.recap.SearchRecapFragment
import com.puntogris.recap.ui.search.user.SearchUserFragment
import com.puntogris.recap.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        binding.fragment = this
        setupViewPager()
        registerToolbarBackButton(binding.toolbar)
        registerQueryTextListener()
        subscribeFilterButtonVisibility()
    }

    private fun registerQueryTextListener(){
        binding.searchTextInputLayout.editText?.apply {

            if (text.isNullOrBlank()) focusAndShowKeyboard()

            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.updateQuery(text.toString())
                    hideKeyboard()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun subscribeFilterButtonVisibility(){
        viewModel.queryLiveData.observe(viewLifecycleOwner) {
            binding.filterButton.setVisibility(binding.viewPager.currentItem == 0 && it.isNotBlank())
        }
    }

    private fun setupViewPager(){
        binding.viewPager.apply {
            adapter = ScreenSlidePagerAdapter(childFragmentManager)
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){

                override fun onPageSelected(position: Int) {
                    binding.filterButton.setVisibility(
                        position == 0 && !viewModel.queryLiveData.value.isNullOrBlank()
                    )
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

    private inner class ScreenSlidePagerAdapter(@NonNull parentFragment: FragmentManager):
        FragmentStateAdapter(parentFragment, viewLifecycleOwner.lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int) =
            if (position == 0 ) SearchRecapFragment() else SearchUserFragment()
    }

    fun showFilterBottomSheet(){
        findNavController().navigate(R.id.searchRecapFilterBottomSheet)
    }

    fun navigateToRecap(recap: Recap){
        val action = SearchFragmentDirections.actionSearchFragmentToRecapFragment(recap)
        findNavController().navigate(action)
    }

    fun showFavoriteSnack(){
        showSnackBar("Agregado a favoritos")
    }


    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}