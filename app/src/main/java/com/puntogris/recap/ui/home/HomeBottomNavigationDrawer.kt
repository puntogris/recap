package com.puntogris.recap.ui.home

import android.app.Dialog
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.puntogris.recap.R
import com.puntogris.recap.databinding.MainBottomNavigationDrawerBinding
import com.puntogris.recap.databinding.MainBottomNavigationDrawerProfileContentBinding
import com.puntogris.recap.databinding.MainBottomNavigationDrawerProfileHeaderBinding
import com.puntogris.recap.ui.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeBottomNavigationDrawer :
    BaseBottomSheetFragment<MainBottomNavigationDrawerBinding>(R.layout.main_bottom_navigation_drawer) {

    private val viewModel: HomeViewModel by viewModels()

    private val navigationDrawerContentBinding:
            MainBottomNavigationDrawerProfileContentBinding by viewBinding(R.id.bottom_navigation_content)

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

        viewModel.authorizedLiveData.observe(viewLifecycleOwner) {
            with(navigationDrawerContentBinding.headerNavigationView.menu) {
                setGroupVisible(R.id.group_unauthorized, !it)
                setGroupVisible(R.id.group_authorized, it)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)

        bottomSheetDialog.setOnShowListener {
            val bottomSheet = bottomSheetDialog
                .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet).apply {
                skipCollapsed = true
                state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return bottomSheetDialog
    }

}
