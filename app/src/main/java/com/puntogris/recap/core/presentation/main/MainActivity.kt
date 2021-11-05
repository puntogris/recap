package com.puntogris.recap.core.presentation.main

import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import com.puntogris.recap.R
import com.puntogris.recap.databinding.ActivityMainBinding
import com.puntogris.recap.core.presentation.base.BaseBindingActivity
import com.puntogris.recap.core.utils.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun preInitViews() {
        installSplashScreen()
    }

    override fun initializeViews() {
        setupNavigation()
        checkAppCurrentVersion()
    }

    private fun setupNavigation() {
        navController = getNavController()
    }

    private fun checkAppCurrentVersion() {
        viewModel.appVersionStatus.observe(this) { isNewVersion ->
            if (isNewVersion) navController.navigate(R.id.whatsNewDialog)
        }
    }

    override fun showSnackBar(
        message: String,
        duration: Int,
        actionText: Int,
        anchorView: View?,
        actionListener: View.OnClickListener?
    ) {

        Snackbar.make(binding.root, message, duration).apply {
            if (actionListener != null) setAction(actionText, actionListener)
            show()
        }
    }
}