package com.puntogris.recap.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.puntogris.recap.R

class PreferencesFragment: PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
    }

}