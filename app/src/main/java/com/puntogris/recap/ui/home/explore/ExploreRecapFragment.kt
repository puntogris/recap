package com.puntogris.recap.ui.home.explore

import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreRecapBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.HomeViewModel
import com.puntogris.recap.utils.PagingStateListener
import com.puntogris.recap.utils.pagingStateListener
import com.puntogris.recap.utils.scrollToTop
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
        adapter = ExploreRecapAdapter(::onRecapShortClick, ::onRecapLongClick).also {
            binding.recyclerView.adapter = it

            stateListener = pagingStateListener { state ->
                binding.contentLoadingLayout.registerState(state.refresh is LoadState.Loading)
            }

            it.addLoadStateListener(stateListener)

            collectUiState(it)
        }

        viewModel.reselectedTabId.observe(viewLifecycleOwner){
            if (it == id) binding.recyclerView.scrollToTop()
        }
    }

    private fun collectUiState(adapter: ExploreRecapAdapter){
        viewModel.recapsLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
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