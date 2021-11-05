package com.puntogris.recap.feature_search.presentation.user

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchUserBinding
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.core.presentation.base.BasePagerTabFragment
import com.puntogris.recap.feature_search.presentation.SearchFragment
import com.puntogris.recap.feature_search.presentation.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment :
    BasePagerTabFragment<FragmentSearchUserBinding>(R.layout.fragment_search_user) {

    override val viewModel: SearchViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override val adapter = PublicProfileAdapter(::onProfileClicked)

    override val recyclerView: RecyclerView
        get() = binding.recyclerView

    override fun onAdapterLoadStateChanged(state: CombinedLoadStates) {
        with(binding) {
            emptyStateLayout.root.isVisible = adapter.itemCount == 0
            contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
        }
    }

    override fun initializeViews() {
        super.initializeViews()
        collectUiState()
    }

    private fun collectUiState() {
        viewModel.usersLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onProfileClicked(publicProfile: PublicProfile) {
        (requireParentFragment() as SearchFragment).navigateToProfile(publicProfile)
    }
}