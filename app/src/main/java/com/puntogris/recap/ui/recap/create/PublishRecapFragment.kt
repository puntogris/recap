package com.puntogris.recap.ui.recap.create

import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentPublishRecapBinding
import com.puntogris.recap.ui.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublishRecapFragment : BaseBindingFragment<FragmentPublishRecapBinding>(R.layout.fragment_publish_recap) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph){defaultViewModelProviderFactory}

    override fun initializeViews() {

    }

    fun onContinueButtonClicked(){
        val nav = NavOptions.Builder().setPopUpTo(R.id.navigation, true).build()
        findNavController().navigate(R.id.homeFragment, null, nav)
    }
}