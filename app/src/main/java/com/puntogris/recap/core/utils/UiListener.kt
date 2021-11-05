package com.puntogris.recap.core.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.puntogris.recap.R

interface UiListener {
    fun showSnackBar(
        message: String,
        duration: Int = Snackbar.LENGTH_LONG,
        actionText: Int = R.string.action_undo,
        anchorView: View? = null,
        actionListener: View.OnClickListener? = null
    )
}