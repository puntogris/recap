package com.puntogris.recap.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.puntogris.recap.R
import com.puntogris.recap.utils.Constants.CROSS_FADE_DURATION
import jp.wasabeef.richeditor.RichEditor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun EditText.getString() = text.toString()

fun EditText.getIntOrNull() = text.toString().toIntOrNull()

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

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let { requireActivity().hideKeyboard(it) }
}
fun ExtendedFloatingActionButton.setVisibility(visible: Boolean) {
    if (visible) show() else hide()
}

fun View.focusAndShowKeyboard() {
    fun View.showTheKeyboardNow() {
        if (isFocused) {
            post {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    requestFocus()

    if (hasFocus()) {
        showTheKeyboardNow()
    } else {
        viewTreeObserver.addOnWindowFocusChangeListener(
            object : ViewTreeObserver.OnWindowFocusChangeListener {
                override fun onWindowFocusChanged(hasFocus: Boolean) {
                    if (hasFocus) {
                        this@focusAndShowKeyboard.showTheKeyboardNow()
                        viewTreeObserver.removeOnWindowFocusChangeListener(this)
                    }
                }
            })
    }
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
    setOnPreferenceChangeListener { _, newValue ->
        block(newValue)
        true
    }
}

inline fun PreferenceFragmentCompat.onPreferenceChange(key: String, crossinline block: (Any) -> Unit){
    findPreference<Preference>(key)?.setOnPreferenceChangeListener { _, newValue ->
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

fun RichEditor.setupBackgroundAndFontColors(){
    setEditorBackgroundColor(ResourcesCompat.getColor(resources, R.color.color_background, null))
    setEditorFontColor(ResourcesCompat.getColor(resources, R.color.color_on_background, null))
}

typealias PagingStateListener = (CombinedLoadStates) -> Unit

fun RecyclerView.scrollToTop() {
    layoutManager?.let {
        val firstVisibleItemPosition = it.findFirstVisibleItemPosition()
        if (firstVisibleItemPosition > 6) {
            scrollToPosition(6)
        }
        smoothScrollToPosition(0)

        if (it is StaggeredGridLayoutManager) {
            it.invalidateSpanAssignments()
        }
    }
}

fun RecyclerView.LayoutManager?.findFirstVisibleItemPosition(): Int {
    return when (this) {
        is LinearLayoutManager -> findFirstVisibleItemPosition()
        is GridLayoutManager -> findFirstVisibleItemPosition()
        is StaggeredGridLayoutManager -> findFirstVisibleItemPositions(null).first()
        else -> RecyclerView.NO_POSITION
    }
}

inline fun EditText.onImeActionSearch(crossinline block: () -> Unit){
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            block()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}

fun Fragment.launchWebBrowserIntent(uri: String, packageName: String? = null){
    try {
        Intent(Intent.ACTION_VIEW).let {
            it.data = Uri.parse(uri)
            if (packageName != null) it.setPackage(packageName)
            startActivity(it)
        }

    }catch (e:Exception){
        showSnackBar(getString(R.string.snack_general_error))
    }
}
