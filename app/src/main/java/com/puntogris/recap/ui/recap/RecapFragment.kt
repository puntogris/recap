package com.puntogris.recap.ui.recap

import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecapFragment : BaseFragment<FragmentRecapBinding>(R.layout.fragment_recap) {

    private val viewModel: RecapViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.recapEditor.setEditorBackgroundColor(Color.BLACK)
        binding.recapEditor.setEditorFontColor(Color.WHITE)
    }

    fun onFavoriteClicked(){
        showSnackBar("Agregado a favoritos")

    }

    fun onLikedClicked(){
    }

    fun onShareClicked(){

    }
}