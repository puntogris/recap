package com.puntogris.recap.ui.recap.create

import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentReviewRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.registerToolbarBackButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewRecapFragment : BaseFragment<FragmentReviewRecapBinding>(R.layout.fragment_review_recap) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph){defaultViewModelProviderFactory}

    override fun initializeViews() {
        binding.fragment = this

        registerToolbarBackButton(binding.toolbar)
    }

    fun navigateToPublish(){
        findNavController().navigate(R.id.action_reviewRecapFragment_to_publishRecapFragment)
    }
}