package com.puntogris.recap.ui.explore.reviews

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreRecapBinding
import com.puntogris.recap.databinding.FragmentExploreReviewBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.explore.ExploreAdapter
import com.puntogris.recap.ui.explore.ExploreFragment
import com.puntogris.recap.ui.explore.ExploreViewModel
import com.puntogris.recap.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ExploreReviewFragment : BaseFragment<FragmentExploreReviewBinding>(R.layout.fragment_explore_review) {

    private val viewModel: ExploreViewModel by viewModels()

    override fun initializeViews() {
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        val adapter = ExploreAdapter({ onRecapShortClick(it) },{ onRecapLongClick(it) })
        binding.recyclerView.adapter = adapter
        collectUiState(adapter)
    }

    private fun collectUiState(adapter: ExploreAdapter){
        launchAndRepeatWithViewLifecycle(Lifecycle.State.CREATED) {
            viewModel.reviewsFlow.collectLatest {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener { state ->
            binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
        }
    }

    private fun onRecapShortClick(recap: Recap){
        findNavController().navigate(R.id.recapFragment)
    }

    //add the recap to fav
    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as ExploreFragment).showFavoriteSnack()
    }

}