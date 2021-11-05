package com.puntogris.recap.feature_settings.presentation.about

import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentAboutBinding
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.registerToolbarBackButton

class AboutFragment : BaseBindingFragment<FragmentAboutBinding>(R.layout.fragment_about) {

    override fun initializeViews() {
        inflatePreferences()
        registerToolbarBackButton(binding.toolbar)
    }

    private fun inflatePreferences() {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, AboutPreferences())
            .commit()
    }
}