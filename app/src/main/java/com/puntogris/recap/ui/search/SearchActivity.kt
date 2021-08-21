package com.puntogris.recap.ui.search

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.puntogris.recap.R
import com.puntogris.recap.databinding.ActivitySearchBinding
import com.puntogris.recap.ui.base.BaseActivity

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    override fun initializeViews() {

    }

    private fun ExtendedFloatingActionButton.setVisibility(visible: Boolean) {
        if (visible) show() else hide()
    }

}