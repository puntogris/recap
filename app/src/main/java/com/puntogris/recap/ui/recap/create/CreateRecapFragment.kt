package com.puntogris.recap.ui.recap.create

import androidx.navigation.fragment.findNavController
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentCreateRecapBinding
import com.puntogris.recap.model.Draft
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.registerToolbarBackButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRecapFragment : BaseFragment<FragmentCreateRecapBinding>(R.layout.fragment_create_recap) {

    override fun initializeViews() {
        binding.fragment = this
        registerToolbarBackButton(binding.toolbar)
    }

    fun navigateToDraft(){
        val draft = Draft()
        val action = CreateRecapFragmentDirections.actionCreateRecapFragmentToDraftFragment(draft)
        findNavController().navigate(action)
    }
}