package com.puntogris.recap.utils

import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.puntogris.recap.R
import com.puntogris.recap.ui.about.AboutPreferences
import com.puntogris.recap.utils.Constants.CROSS_FADE_DURATION
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

fun Fragment.registerToolbarBackButton(toolbar: MaterialToolbar){
    toolbar.setNavigationOnClickListener {
        findNavController().navigateUp()
    }
}

fun ExtendedFloatingActionButton.setVisibility(visible: Boolean) {
    if (visible) show() else hide()
}

inline fun <T: Preference>PreferenceFragmentCompat.preference(key: String, block: T.() -> Unit){
    findPreference<T>(key)?.apply {
        block(this)
    }
}

inline fun Preference.onClick(crossinline block: () -> Unit){
    setOnPreferenceClickListener {
        block()
        true
    }
}

inline fun Preference.onChange(crossinline block: (Any) -> Unit){
    setOnPreferenceChangeListener { preference, newValue ->
        block(newValue)
        true
    }
}

inline fun PreferenceFragmentCompat.preferenceOnClick(key: String, crossinline block: () -> Unit){
    findPreference<Preference>(key)?.setOnPreferenceClickListener {
        block()
        true
    }
}

inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}

fun ImageView.loadProfilePicture(url: String?) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .transition(DrawableTransitionOptions.withCrossFade(CROSS_FADE_DURATION))
        .into(this)
        .clearOnDetach()
}
