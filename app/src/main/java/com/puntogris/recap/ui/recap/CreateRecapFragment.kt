package com.puntogris.recap.ui.recap

import android.graphics.Color
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentCreateRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRecapFragment : BaseFragment<FragmentCreateRecapBinding>(R.layout.fragment_create_recap) {

    override fun initializeViews() {
        binding.recapEditor.setEditorBackgroundColor(Color.BLACK)
        binding.recapEditor.setEditorFontColor(Color.WHITE)

    }

    private var fontSize = 1


}