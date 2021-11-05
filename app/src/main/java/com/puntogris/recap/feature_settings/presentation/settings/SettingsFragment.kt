package com.puntogris.recap.feature_settings.presentation.settings

import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.registerToolbarBackButton
import com.puntogris.recap.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseBindingFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    override fun initializeViews() {
        inflatePreferences()
        registerToolbarBackButton(binding.toolbar)
    }

    private fun inflatePreferences() {
        childFragmentManager
            .beginTransaction()
            .replace(binding.container.id, PreferencesFragment())
            .commit()
    }
}