package com.puntogris.recap.ui.home.explore

import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreRecapBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.HomeViewModel
import com.puntogris.recap.utils.PagingStateListener
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class ExploreRecapFragment : BaseFragment<FragmentExploreRecapBinding>(R.layout.fragment_explore_recap) {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = {requireParentFragment()})
    private lateinit var adapter: ExploreRecapAdapter
    private lateinit var stateListener: PagingStateListener

    override fun initializeViews() {
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        adapter = ExploreRecapAdapter({ onRecapShortClick(it) },{ onRecapLongClick(it) }).also {
            binding.recyclerView.adapter = it
            collectUiState(it)
        }
    }

    private fun collectUiState(adapter: ExploreRecapAdapter){
        viewModel.recapsLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        stateListener = object : PagingStateListener {
            override operator fun invoke(loadState: CombinedLoadStates) {
                 binding.contentLoadingLayout.registerState(loadState.refresh is LoadState.Loading)
            }
        }

        adapter.addLoadStateListener(stateListener)
    }

    private fun onRecapShortClick(recap: Recap){
        (requireParentFragment() as HomeFragment).navigateToRecap(recap)
    }

    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as HomeFragment).showFavoriteSnack()
    }

    override fun onDestroyView() {
        adapter.removeLoadStateListener(stateListener)
        super.onDestroyView()
    }
}