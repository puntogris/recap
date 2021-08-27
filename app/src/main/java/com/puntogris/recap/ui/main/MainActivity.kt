package com.puntogris.recap.ui.main

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.puntogris.recap.R
import com.puntogris.recap.ui.base.BaseActivity
import com.puntogris.recap.databinding.ActivityMainBinding
import com.puntogris.recap.utils.getNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun initializeViews() {
        setupNavigation()
        setupBottomAppBar()
    }

    private fun setupNavigation() {
        navController = getNavController()
    }

    private fun setupBottomAppBar(){
        binding.bottomAppBar.apply {
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_search -> {
                        findNavController().navigate(R.id.searchFragment)
                        true
                    }
                    else -> true
                }
            }
            setNavigationOnClickListener {

            }
        }
    }

}