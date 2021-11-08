package com.puntogris.recap.feature_recap.presentation.main_feed.reviews

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BasePagerTabFragment
import com.puntogris.recap.databinding.FragmentExploreReviewBinding
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.presentation.main_feed.HomeFragment
import com.puntogris.recap.feature_recap.presentation.main_feed.HomeViewModel
import com.puntogris.recap.feature_recap.presentation.main_feed.explore.ExploreRecapAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreReviewFragment :
    BasePagerTabFragment<FragmentExploreReviewBinding>(R.layout.fragment_explore_review) {

    override val viewModel: HomeViewModel by viewModels()

    override val adapter = ExploreRecapAdapter(::onRecapShortClick, ::onRecapLongClick)

    override val recyclerView: RecyclerView
        get() = binding.recyclerView

    override fun onAdapterLoadStateChanged(state: CombinedLoadStates) {
        binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
    }

    override fun initializeViews() {
        super.initializeViews()
        collectUiState()
    }

    private fun collectUiState() {
        viewModel.reviewsLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onRecapShortClick(recap: Recap) {
        (requireParentFragment() as HomeFragment).navigateToRecap(recap)
    }

    private fun onRecapLongClick(recap: Recap) {
        (requireParentFragment() as HomeFragment).showFavoriteSnack()
    }
}