package com.puntogris.recap.ui.search.recap

import androidx.fragment.app.viewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchRecapBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.explore.ExploreRecapAdapter
import com.puntogris.recap.ui.search.SearchFragment
import com.puntogris.recap.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecapFragment : BaseFragment<FragmentSearchRecapBinding>(R.layout.fragment_search_recap) {

    private val viewModel: SearchViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun initializeViews() {
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        val adapter = ExploreRecapAdapter({onRecapShortClick(it)}, {onRecapLongClick(it)})
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: ExploreRecapAdapter){
        viewModel.recapsLiveData.observe(viewLifecycleOwner){
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onRecapShortClick(recap: Recap){
        (requireParentFragment() as SearchFragment).navigateToRecap(recap)
    }

    private fun onRecapLongClick(recap: Recap){
        (requireParentFragment() as SearchFragment).showFavoriteSnack()
    }
}