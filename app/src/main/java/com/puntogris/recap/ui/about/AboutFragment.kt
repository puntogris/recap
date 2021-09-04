package com.puntogris.recap.ui.about

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentAboutBinding
import com.puntogris.recap.ui.base.BaseFragment

class AboutFragment: BaseFragment<FragmentAboutBinding>(R.layout.fragment_about) {

    override fun initializeViews() {
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.title = "About"

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, AboutPreferences())
            .commit()
    }

    class AboutPreferences: PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.about_preferences, rootKey)
        }
    }
}