package com.puntogris.recap.ui.settings

import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentSettingsBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.registerToolbarBackButton

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    override fun initializeViews() {
        inflatePreferences()
        registerToolbarBackButton(binding.toolbar)
    }

    private fun inflatePreferences(){
        childFragmentManager
            .beginTransaction()
            .replace(binding.container.id, PreferencesFragment())
            .commit()
    }

}