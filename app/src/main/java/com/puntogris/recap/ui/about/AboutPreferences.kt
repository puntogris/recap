package com.puntogris.recap.ui.about

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.puntogris.recap.R

class AboutPreferences: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.about_preferences, rootKey)
    }
}