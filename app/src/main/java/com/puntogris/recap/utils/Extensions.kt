package com.puntogris.recap.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.puntogris.recap.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}
