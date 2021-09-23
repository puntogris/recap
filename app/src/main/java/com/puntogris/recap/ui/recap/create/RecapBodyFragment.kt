package com.puntogris.recap.ui.recap.create

import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRecapBodyBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.focusAndShowKeyboard
import com.puntogris.recap.utils.registerToolbarBackButton
import com.puntogris.recap.utils.setupBackgroundAndFontColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecapBodyFragment : BaseFragment<FragmentRecapBodyBinding>(R.layout.fragment_recap_body) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph){defaultViewModelProviderFactory}

    override fun initializeViews() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        registerToolbarBackButton(binding.toolbar)
        setupEditor()
    }

    private fun setupEditor(){
        binding.recapEditor.apply {
            setupBackgroundAndFontColors()
            focusAndShowKeyboard()
        }
    }

    fun onDoneFabClicked(){
        //mostrar bottom sheet o 2 fabs arriba para guardar en borrador o publicar todo

    }
}

