package com.puntogris.recap.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.puntogris.recap.R
import com.puntogris.recap.utils.*
import com.puntogris.recap.utils.Constants.PREF_APP_THEME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesFragment: PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        onPreferenceChange(PREF_APP_THEME){ newValue ->
            ThemeUtils.applyTheme(newValue.toString())
        }
    }
}

