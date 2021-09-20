package com.puntogris.recap.ui.home.reviews

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreReviewBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.base.BasePagerTabFragment
import com.puntogris.recap.ui.home.explore.ExploreRecapAdapter
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.HomeViewModel
import com.puntogris.recap.utils.PagingStateListener
import com.puntogris.recap.utils.pagingStateListener
import com.puntogris.recap.utils.scrollToTop
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreReviewFragment : BasePagerTabFragment<FragmentExploreReviewBinding>(R.layout.fragment_explore_review) {

    override val viewModel: HomeViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override val adapter = ExploreRecapAdapter(::onRecapShortClick, ::onRecapLongClick)

    override val recyclerView: RecyclerView
        get() = binding.recyclerView

    override fun onAdapterLoadStateChanged(state: CombinedLoadStates) {
        binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
    }

    override fun initializeViews() {
        collectUiState()
    }

    private fun collectUiState(){
        viewModel.reviewsLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onRecapShortClick(recap: Recap){
        findNavController().navigate(R.id.recapFragment)
    }

    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as HomeFragment).showFavoriteSnack()
    }
}