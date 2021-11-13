package com.puntogris.recap.feature_settings.presentation.settings

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.puntogris.recap.R
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.core.utils.Constants.PREF_APP_THEME
import com.puntogris.recap.core.utils.ThemeUtils
import com.puntogris.recap.core.utils.onPreferenceChange
import com.puntogris.recap.core.utils.preferenceOnClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreferencesFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        onPreferenceChange(PREF_APP_THEME) { newValue ->
            ThemeUtils.applyTheme(newValue.toString())
        }

        preferenceOnClick(Constants.DELETE_ACCOUNT){
            findNavController().navigate(R.id.deleteAccountFragment)
        }
    }
}

