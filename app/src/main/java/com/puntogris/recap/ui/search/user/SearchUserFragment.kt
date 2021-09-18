package com.puntogris.recap.ui.search.user

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchUserBinding
import com.puntogris.recap.models.PublicProfile
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.search.SearchViewModel
import com.puntogris.recap.utils.PagingStateListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : BaseFragment<FragmentSearchUserBinding>(R.layout.fragment_search_user) {

    private val viewModel: SearchViewModel by viewModels(ownerProducer = {requireParentFragment()})
    private lateinit var stateListener: PagingStateListener
    private lateinit var adapter: PublicProfileAdapter

    override fun initializeViews() {
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        adapter = PublicProfileAdapter(::onProfileClicked).also {
            binding.recyclerView.adapter = it
            collectUiState(it)
        }
    }

    private fun collectUiState(adapter: PublicProfileAdapter){
        viewModel.usersLiveData.observe(viewLifecycleOwner){
            adapter.submitData(lifecycle, it)
        }

        stateListener = object : PagingStateListener {
            override operator fun invoke(loadState: CombinedLoadStates) {
                binding.emptyStateLayout.root.isVisible = adapter.itemCount == 0
                binding.contentLoadingLayout.registerState(loadState.refresh is LoadState.Loading)            }
        }
        adapter.addLoadStateListener(stateListener)
    }

    private fun onProfileClicked(publicProfile: PublicProfile){

    }

    override fun onDestroyView() {
        adapter.removeLoadStateListener(stateListener)
        super.onDestroyView()
    }
}