package com.puntogris.recap.ui.explore.recaps

import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadType
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreRecapBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.explore.ExploreAdapter
import com.puntogris.recap.ui.explore.ExploreFragment
import com.puntogris.recap.ui.explore.ExploreViewModel
import com.puntogris.recap.utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ExploreRecapFragment : BaseFragment<FragmentExploreRecapBinding>(R.layout.fragment_explore_recap) {

    private val viewModel: ExploreViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun initializeViews() {
        setupRecyclerViewAdapter()

        requireParentFragment().setFragmentResultListener("data"){requestKey, bundle ->
            viewModel.orderRecapsBy(bundle["order"].toString().toInt())
        }
    }

    private fun setupRecyclerViewAdapter(){
        val adapter = ExploreAdapter({ onRecapShortClick(it) },{ onRecapLongClick(it) })
        binding.recyclerView.adapter = adapter
        collectUiState(adapter)
    }

    private fun collectUiState(adapter: ExploreAdapter){
        launchAndRepeatWithViewLifecycle(Lifecycle.State.CREATED) {
            viewModel.recapsFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun onRecapShortClick(recap: Recap){
        findNavController().navigate(R.id.recapFragment)
    }

    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as ExploreFragment).showFavoriteSnack()
    }

}