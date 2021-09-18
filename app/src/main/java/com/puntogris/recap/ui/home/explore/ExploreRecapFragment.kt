package com.puntogris.recap.ui.home.explore

import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreRecapBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreRecapFragment : BaseFragment<FragmentExploreRecapBinding>(R.layout.fragment_explore_recap) {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun initializeViews() {
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        val adapter = ExploreRecapAdapter({ onRecapShortClick(it) },{ onRecapLongClick(it) })
        binding.recyclerView.adapter = adapter
        collectUiState(adapter)
    }

    private fun collectUiState(adapter: ExploreRecapAdapter){
        viewModel.recapsLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        adapter.addLoadStateListener { state ->
            binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
        }
    }

    private fun onRecapShortClick(recap: Recap){
        (requireParentFragment() as HomeFragment).navigateToRecap(recap)
    }

    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as HomeFragment).showFavoriteSnack()
    }

}