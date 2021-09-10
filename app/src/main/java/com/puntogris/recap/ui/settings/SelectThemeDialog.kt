package com.puntogris.recap.ui.settings

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.R
import com.puntogris.recap.utils.SharedPref
import com.puntogris.recap.utils.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class SelectThemeDialog: DialogFragment(){

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val themeValues = resources.getStringArray(R.array.theme_values)

        val currentPosition = runBlocking {
            themeValues.indexOf(sharedPref.appTheme())
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setNegativeButton("Cancelar"){ _, _ -> dismiss() }
            .setSingleChoiceItems(R.array.theme_options, currentPosition){ _, position ->
                saveAndApplyThemePreference(themeValues[position])
            }
            .create()
    }

    private fun saveAndApplyThemePreference(theme: String){
        lifecycleScope.launch {
            sharedPref.setAppThemePreference(theme)
            ThemeUtils.applyTheme(theme)
            dismiss()
        }
    }
}
