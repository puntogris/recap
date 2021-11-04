package com.puntogris.recap.feature_recap.presentation.create_recap

import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRecapPreviewBinding
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.registerToolbarBackButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecapPreviewFragment : BaseBindingFragment<FragmentRecapPreviewBinding>(R.layout.fragment_recap_preview) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph){defaultViewModelProviderFactory}

    override fun initializeViews() {
        binding.fragment = this

        registerToolbarBackButton(binding.toolbar)
    }

    fun navigateToPublish(){
        findNavController().navigate(R.id.action_reviewRecapFragment_to_publishRecapFragment)
    }
}