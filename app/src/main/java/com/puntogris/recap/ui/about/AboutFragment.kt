package com.puntogris.recap.ui.about

import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentAboutBinding
import com.puntogris.recap.ui.base.BaseBindingFragment
import com.puntogris.recap.utils.registerToolbarBackButton

class AboutFragment: BaseBindingFragment<FragmentAboutBinding>(R.layout.fragment_about) {

    override fun initializeViews() {
        inflatePreferences()
        registerToolbarBackButton(binding.toolbar)
    }

    private fun inflatePreferences(){
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, AboutPreferences())
            .commit()
    }
}