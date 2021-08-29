package com.puntogris.recap.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.puntogris.recap.R

fun AppCompatActivity.getNavController() = getNavHostFragment().navController

fun AppCompatActivity.getNavHostFragment() =
    (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)


fun Fragment.showSnackBar(message: String,
                          duration: Int = Snackbar.LENGTH_LONG,
                          actionText: String = "",
                          anchorView: View? = null,
                          actionListener: View.OnClickListener? = null){

    Snackbar.make(requireView(), message, duration).let {
        if (anchorView != null) it.anchorView = anchorView
        if (actionListener != null) it.setAction(actionText, actionListener)
        it.show()
    }
}