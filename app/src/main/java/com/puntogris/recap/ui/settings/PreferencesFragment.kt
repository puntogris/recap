package com.puntogris.recap.ui.settings

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.puntogris.recap.R
import com.puntogris.recap.utils.onClick
import com.puntogris.recap.utils.preference
import com.puntogris.recap.utils.preferenceOnClick

class PreferencesFragment: PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        preferenceOnClick("theme_preference"){
            findNavController().navigate(R.id.selectThemeDialog)
        }
    }

}