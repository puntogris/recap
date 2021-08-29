package com.puntogris.recap.ui.recap

import android.graphics.Color
import androidx.navigation.fragment.findNavController
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentCreateRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRecapFragment : BaseFragment<FragmentCreateRecapBinding>(R.layout.fragment_create_recap) {

    override fun initializeViews() {
        binding.recapEditor.setEditorBackgroundColor(Color.BLACK)
        binding.recapEditor.setEditorFontColor(Color.WHITE)
        binding.toolbar.title = "The Office S04E05"
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }


    }

    fun onDoneFabClicked(){
        //mostrar bottom sheet o 2 fabs arriba para guardar en borrador o publicar

    }

}