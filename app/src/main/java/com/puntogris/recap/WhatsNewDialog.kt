package com.puntogris.recap

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puntogris.recap.BuildConfig
import com.puntogris.recap.R
import com.puntogris.recap.core.utils.launchWebBrowserIntent

class WhatsNewDialog : DialogFragment() {

    override fun onCreateDialog(savedViewState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.updated_version, BuildConfig.VERSION_NAME))
            .setPositiveButton(android.R.string.ok, null)
            .setNeutralButton(R.string.whats_new) { _, _ ->
                launchWebBrowserIntent("https://recap.puntogris.com/versions/v${BuildConfig.VERSION_NAME}.html")
            }
            .create()
    }
}
