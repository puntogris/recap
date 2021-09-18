package com.puntogris.recap.ui.main

import android.net.Uri
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import com.puntogris.recap.R
import com.puntogris.recap.databinding.ActivityMainBinding
import com.puntogris.recap.ui.base.BaseActivity
import com.puntogris.recap.utils.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun preInitViews() {
        installSplashScreen()
    }

    override fun initializeViews() {
        setupNavigation()
    }

    private fun setupNavigation() {
        navController = getNavController()
    }

    override fun showSnackBar(message: String,
                              duration: Int,
                              actionText: Int,
                              anchorView: View?,
                              actionListener: View.OnClickListener?){

        Snackbar.make(binding.root, message, duration).apply {
            if (actionListener != null) setAction(actionText, actionListener)
            show()
        }
    }
}