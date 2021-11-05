package com.puntogris.recap.feature_recap.presentation.create_recap

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRecapBodyBinding
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecapBodyFragment :
    BaseBindingFragment<FragmentRecapBodyBinding>(R.layout.fragment_recap_body) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph) { defaultViewModelProviderFactory }

    override fun initializeViews() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        registerToolbarBackButton(binding.toolbar)
        setupEditor()
    }

    private fun setupEditor() {
        binding.recapEditor.apply {
            setupBackgroundAndFontColors()
            focusAndShowKeyboard()
        }
    }

    fun onSaveRecapClicked(previewOnSuccess: Boolean) {
        viewModel.toggleMenu()

        binding.recapEditor.html.let {
            if (it.isNullOrBlank()) showSnackBar("No puede estar vacio.")
            else {
                viewModel.updateRecapBody(it)
                saveRecap(previewOnSuccess)
            }
        }
    }

    private fun saveRecap(previewOnSuccess: Boolean) {
        lifecycleScope.launch {
            when (viewModel.saveRecapLocally()) {
                SimpleResult.Failure -> showSnackBar("Error")
                SimpleResult.Success -> {
                    showSnackBar("Guardado en borradores")
                    if (previewOnSuccess) {
                        findNavController().navigate(R.id.action_recapBodyFragment_to_previewRecapFragment)
                    }
                }
            }
        }
    }

}

