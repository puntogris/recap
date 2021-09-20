package com.puntogris.recap.ui.about

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.puntogris.recap.R
import com.puntogris.recap.utils.preferenceOnClick

class AboutPreferences: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.about_preferences, rootKey)

        preferenceOnClick("licenses_preference"){
            Intent(requireContext(), OssLicensesMenuActivity::class.java).apply {
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.licenses))
                startActivity(this)
            }
        }
    }
}