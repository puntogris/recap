package com.puntogris.recap.ui.search.user

import androidx.fragment.app.viewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSearchUserBinding
import com.puntogris.recap.models.PublicProfile
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : BaseFragment<FragmentSearchUserBinding>(R.layout.fragment_search_user) {

    private val viewModel: SearchViewModel by viewModels(ownerProducer = {requireParentFragment()})

    override fun initializeViews() {
        setupRecyclerViewAdapter()
    }

    private fun setupRecyclerViewAdapter(){
        val adapter = PublicProfileAdapter(::onProfileClicked)
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: PublicProfileAdapter){
        viewModel.usersLiveData.observe(viewLifecycleOwner){
            adapter.submitData(lifecycle, it)
        }
    }

    private fun onProfileClicked(publicProfile: PublicProfile){
        
    }
}