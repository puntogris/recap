package com.puntogris.recap.ui.user

import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserRecapsBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.home.HomeFragment
import com.puntogris.recap.ui.home.explore.ExploreRecapAdapter
import com.puntogris.recap.utils.PagingStateListener
import com.puntogris.recap.utils.pagingStateListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRecapsFragment : BaseFragment<FragmentUserRecapsBinding>(R.layout.fragment_user_recaps) {

    private val viewModel: UserViewModel by viewModels(ownerProducer = {requireParentFragment()})
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
    }


    private fun collectUiState(adapter: ExploreRecapAdapter){
        viewModel.recapsLiveData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onRecapShortClick(recap: Recap){
      //  (requireParentFragment() as UserFragment).navigateToRecap(recap)
    }

    private fun onRecapLongClick(recap: Recap){
        //(requireParentFragment() as UserFragment).showFavoriteSnack()
    }

    override fun onDestroyView() {
        adapter.removeLoadStateListener(stateListener)
        super.onDestroyView()
    }
}