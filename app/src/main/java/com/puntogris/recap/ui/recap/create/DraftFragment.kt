package com.puntogris.recap.ui.recap.create

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentDraftBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.focusAndShowKeyboard
import com.puntogris.recap.utils.registerToolbarBackButton
import com.puntogris.recap.utils.setupBackgroundAndFontColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DraftFragment : BaseFragment<FragmentDraftBinding>(R.layout.fragment_draft) {

    private val viewModel: CreateRecapViewModel by viewModels()
    private val args: DraftFragmentArgs by navArgs()

    override fun initializeViews() {
        registerToolbarBackButton(binding.toolbar)
        setDraftFromArgs()
        setupEditor()
    }

    private fun setDraftFromArgs(){
        viewModel.setDraft(args.draft)
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