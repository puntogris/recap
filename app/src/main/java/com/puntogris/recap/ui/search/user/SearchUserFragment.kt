package com.puntogris.recap.ui.search.user

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchUserBinding
import com.puntogris.recap.models.PublicProfile
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.search.SearchFragment
import com.puntogris.recap.ui.search.SearchViewModel
import com.puntogris.recap.utils.PagingStateListener
import com.puntogris.recap.utils.pagingStateListener
import com.puntogris.recap.utils.scrollToTop
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : BaseFragment<FragmentSearchUserBinding>(R.layout.fragment_search_user) {

    private val viewModel: SearchViewModel by viewModels(ownerProducer = {requireParentFragment()})
    private lateinit var stateListener: PagingStateListener
    private lateinit var adapter: PublicProfileAdapter

    override fun initializeViews() {
        setupRecyclerViewAdapter()

        viewModel.reselectedTabId.observe(viewLifecycleOwner){
            if (it == id) binding.recyclerView.scrollToTop()
        }
    }

    private fun setupRecyclerViewAdapter(){
        adapter = PublicProfileAdapter(::onProfileClicked).also {
            binding.recyclerView.adapter = it

            stateListener = pagingStateListener { state ->
                binding.emptyStateLayout.root.isVisible = it.itemCount == 0
                binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
            }

            it.addLoadStateListener(stateListener)

            collectUiState(it)
        }
    }

    private fun collectUiState(adapter: PublicProfileAdapter){
        viewModel.usersLiveData.observe(viewLifecycleOwner){
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onProfileClicked(publicProfile: PublicProfile){
        (requireParentFragment() as SearchFragment).navigateToProfile(publicProfile)
    }

    override fun onDestroyView() {
        adapter.removeLoadStateListener(stateListener)
        super.onDestroyView()
    }
}