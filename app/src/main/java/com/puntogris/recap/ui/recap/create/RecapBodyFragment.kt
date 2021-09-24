package com.puntogris.recap.ui.recap.create

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRecapBodyBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecapBodyFragment : BaseFragment<FragmentRecapBodyBinding>(R.layout.fragment_recap_body) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph){defaultViewModelProviderFactory}

    override fun initializeViews() {
        binding.fragment = this
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

    fun onSaveRecapClicked(reviewOnSuccess: Boolean){
        viewModel.toggleMenu()
        val body = binding.recapEditor.html
        if (body.isNullOrBlank()){
            showSnackBar("No puede estar vacio.")
        }else{
            viewModel.updateRecapBody(body)
            saveRecap(reviewOnSuccess)
        }
    }

    private fun saveRecap(reviewOnSuccess: Boolean){
        lifecycleScope.launch {
            when(viewModel.saveRecapLocally()){
                SimpleResult.Failure -> showSnackBar("Error")
                SimpleResult.Success -> {
                    showSnackBar("Guardado en borradores")
                    if (reviewOnSuccess) {
                        findNavController().navigate(R.id.action_recapBodyFragment_to_reviewRecapFragment)
                    }
                }
            }
        }
    }

}

