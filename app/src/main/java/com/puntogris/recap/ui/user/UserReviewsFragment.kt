package com.puntogris.recap.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserReviewsBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.HomeViewModel
import com.puntogris.recap.ui.home.explore.ExploreRecapAdapter
import com.puntogris.recap.utils.PagingStateListener
import com.puntogris.recap.utils.pagingStateListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserReviewsFragment : BaseFragment<FragmentUserReviewsBinding>(R.layout.fragment_user_reviews) {

    private val viewModel: UserViewModel by viewModels(ownerProducer = {requireParentFragment()})
    private lateinit var stateListener: PagingStateListener
    private lateinit var adapter: ExploreRecapAdapter

    override fun initializeViews() {
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        adapter = ExploreRecapAdapter(::onRecapShortClick, ::onRecapLongClick).also {
            binding.recyclerView.adapter = it

            stateListener = pagingStateListener { state ->
                binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
            }

            it.addLoadStateListener(stateListener)

            collectUiState(it)
        }
    }

    private fun collectUiState(adapter: ExploreRecapAdapter){
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

    override fun onDestroyView() {
        adapter.removeLoadStateListener(stateListener)
        super.onDestroyView()
    }

}