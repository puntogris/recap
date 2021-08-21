package com.puntogris.recap.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.puntogris.recap.ui.base.BaseActivity
import com.puntogris.recap.R
import com.puntogris.recap.databinding.ActivityMainBinding
import com.puntogris.recap.ui.search.SearchActivity
import com.puntogris.recap.utils.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun initializeViews() {
        setupNavigation()
        setupBottomAppBar()

    }

    private fun setupBottomAppBar(){
        binding.bottomAppBar.apply {
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_search -> {
                        navController.navigate(R.id.searchActivity)
                        true
                    }
                    else -> true
                }
            }
            setNavigationOnClickListener {

            }
        }
    }

    private fun setupNavigation() {
        navController = getNavController()
    }

}