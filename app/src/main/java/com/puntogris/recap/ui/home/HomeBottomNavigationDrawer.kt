package com.puntogris.recap.ui.home

import androidx.navigation.fragment.findNavController
import com.puntogris.recap.R
import com.puntogris.recap.databinding.MainBottomNavigationDrawerBinding
import com.puntogris.recap.ui.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeBottomNavigationDrawer :
    BaseBottomSheetFragment<MainBottomNavigationDrawerBinding>(R.layout.main_bottom_navigation_drawer) {

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
                else -> true
            }
        }
    }
}
