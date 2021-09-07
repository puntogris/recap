package com.puntogris.recap.ui.home

import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.puntogris.recap.R
import com.puntogris.recap.databinding.MainBottomNavigationDrawerBinding
import com.puntogris.recap.databinding.MainBottomNavigationDrawerProfileContentBinding
import com.puntogris.recap.databinding.MainBottomNavigationDrawerProfileHeaderBinding
import com.puntogris.recap.ui.base.BaseBottomSheetFragment
import com.puntogris.recap.utils.SimpleResult
import com.puntogris.recap.utils.loadProfilePicture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeBottomNavigationDrawer :
    BaseBottomSheetFragment<MainBottomNavigationDrawerBinding>(R.layout.main_bottom_navigation_drawer) {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = {requireParentFragment()})

    private val navigationDrawerContentBinding:
            MainBottomNavigationDrawerProfileContentBinding by viewBinding(R.id.bottom_navigation_content)

    private val navigationDrawerHeaderBinding:
            MainBottomNavigationDrawerProfileHeaderBinding by viewBinding(R.id.bottom_navigation_header)

    override fun initializeViews() {

    }

    override fun viewCreated() {

        navigationDrawerContentBinding.headerNavigationView.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
        }
        binding.drawerNavigationView.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
        }

        viewModel.authorizedLiveData.observe(viewLifecycleOwner) {
            with(navigationDrawerContentBinding.headerNavigationView.menu) {
                setGroupVisible(R.id.group_unauthorized, !it)
                setGroupVisible(R.id.group_authorized, it)
            }
        }

        viewModel.usernameLiveData.observe(viewLifecycleOwner) {
            navigationDrawerHeaderBinding.headerTitle.text = it ?: getString(R.string.app_name)
        }
        viewModel.emailLiveData.observe(viewLifecycleOwner) {
            navigationDrawerHeaderBinding.headerSubtitle.text = it ?: "...."
        }
        viewModel.profilePictureLiveData.observe(viewLifecycleOwner) { url ->
            url?.let {
                navigationDrawerHeaderBinding.headerImageView.loadProfilePicture(it)
            } ?: run {
                navigationDrawerHeaderBinding.headerImageView.setImageResource(R.mipmap.ic_launcher_round)
            }
        }
    }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_add_account -> findNavController().navigate(R.id.loginFragment)
            R.id.action_view_profile -> findNavController().navigate(R.id.userFragment)
            R.id.action_edit_profile -> { }
            R.id.action_log_out -> handleLogOut()
            R.id.action_settings -> findNavController().navigate(R.id.settingsFragment)
            R.id.action_about -> findNavController().navigate(R.id.aboutFragment)
        }
        return true
    }

    private fun handleLogOut(){
        lifecycleScope.launch {
            when(viewModel.logOut()){
                SimpleResult.Failure -> {}
                SimpleResult.Success -> {
                    dismiss()
                    setFragmentResult("home_fragment", bundleOf("log_out_result" to true))
                }
            }
        }
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val bottomSheetDialog = super.onCreateDialog(savedInstanceState)
//
//        bottomSheetDialog.setOnShowListener {
//            val bottomSheet = bottomSheetDialog
//                .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
//            BottomSheetBehavior.from(bottomSheet).apply {
//                skipCollapsed = true
//                state = BottomSheetBehavior.STATE_EXPANDED
//            }
//        }
//
//        return bottomSheetDialog
//    }

    companion object {
        val TAG: String = HomeBottomNavigationDrawer::class.java.simpleName

        fun newInstance() = HomeBottomNavigationDrawer()
    }
}
