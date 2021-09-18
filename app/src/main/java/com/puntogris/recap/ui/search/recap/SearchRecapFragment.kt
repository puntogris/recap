package com.puntogris.recap.ui.search.recap

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.map
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchRecapBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.explore.ExploreRecapAdapter
import com.puntogris.recap.ui.search.SearchFragment
import com.puntogris.recap.ui.search.SearchViewModel
import com.puntogris.recap.utils.gone
import com.puntogris.recap.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchRecapFragment : BaseFragment<FragmentSearchRecapBinding>(R.layout.fragment_search_recap) {

    private val viewModel: SearchViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun initializeViews() {
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        ExploreRecapAdapter({onRecapShortClick(it)}, {onRecapLongClick(it)}).let {
            binding.recyclerView.adapter = it
            collectUiState(it)
        }
    }

    private fun collectUiState(adapter: ExploreRecapAdapter){
        viewModel.recapsLiveData.observe(viewLifecycleOwner){
            adapter.submitData(lifecycle, it)
        }
        adapter.addLoadStateListener { state ->
            binding.emptyStateLayout.root.isVisible = adapter.itemCount == 0
            binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
        }
    }

    private fun onRecapShortClick(recap: Recap){
        (requireParentFragment() as SearchFragment).navigateToRecap(recap)
    }

    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as SearchFragment).showFavoriteSnack()
    }
}