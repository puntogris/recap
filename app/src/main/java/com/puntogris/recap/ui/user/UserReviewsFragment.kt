package com.puntogris.recap.ui.user

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserReviewsBinding
import com.puntogris.recap.model.Recap
import com.puntogris.recap.ui.base.BasePagerTabFragment
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.explore.ExploreRecapAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserReviewsFragment : BasePagerTabFragment<FragmentUserReviewsBinding>(R.layout.fragment_user_reviews) {

    override val viewModel: UserViewModel by viewModels(ownerProducer = {requireParentFragment()})

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

    private fun collectUiState(){
        viewModel.reviewsLiveData.observe(viewLifecycleOwner) {
          //  adapter.submitData(lifecycle, it)
        }
    }

    private fun onRecapShortClick(recap: Recap){
        findNavController().navigate(R.id.recapFragment)
    }

    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as HomeFragment).showFavoriteSnack()
    }

}