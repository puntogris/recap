package com.puntogris.recap.ui.settings

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.puntogris.recap.R
import com.puntogris.recap.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PreferencesFragment: PreferenceFragmentCompat(){

    @Inject lateinit var sharedPref: SharedPref

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        preference<Preference>(SharedPref.PREF_APP_THEME){
           onChange { newValue ->
               ThemeUtils.applyTheme(newValue.toString())
           }
        }
    }
}

