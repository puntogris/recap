package com.puntogris.recap.ui.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.puntogris.recap.R
import com.puntogris.recap.databinding.MainBottomNavigationDrawerBinding
import com.puntogris.recap.ui.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeBottomNavigationDrawer :
    BaseBottomSheetFragment<MainBottomNavigationDrawerBinding>(R.layout.main_bottom_navigation_drawer) {

    private val viewModel: HomeViewModel by viewModels()

    override fun initializeViews() {
        binding.drawerNavigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_about ->{
                    findNavController().navigate(R.id.aboutFragment)
                    true
                }
                R.id.action_settings ->{
                    findNavController().navigate(R.id.settingsFragment)
                    true
                }
                R.id.action_user -> {
                    findNavController().navigate(
                        if (viewModel.isUserLoggedIn()) R.id.userFragment else R.id.loginFragment
                    )
                    true
                }
                else -> true
            }
        }
    }
}
