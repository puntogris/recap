package com.puntogris.recap.ui.search.recap

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.*
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchRecapBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BasePagerTabFragment
import com.puntogris.recap.ui.home.explore.ExploreRecapAdapter
import com.puntogris.recap.ui.search.SearchFragment
import com.puntogris.recap.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecapFragment : BasePagerTabFragment<FragmentSearchRecapBinding>(R.layout.fragment_search_recap) {

    override val viewModel: SearchViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override val adapter = ExploreRecapAdapter(::onRecapShortClick, ::onRecapLongClick)

    override val recyclerView: RecyclerView
        get() = binding.recyclerView

    override fun onAdapterLoadStateChanged(state: CombinedLoadStates) {
        binding.emptyStateLayout.root.isVisible = adapter.itemCount == 0
        binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
    }

    override fun initializeViews() {
        super.initializeViews()
        collectUiState()
    }

    private fun collectUiState(){
        viewModel.recapsLiveData.observe(viewLifecycleOwner){
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onRecapShortClick(recap: Recap){
        (requireParentFragment() as SearchFragment).navigateToRecap(recap)
    }

    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as SearchFragment).showFavoriteSnack()
    }
}