package com.puntogris.recap.feature_recap.presentation.main_feed

import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.puntogris.recap.NavigationDirections
import com.puntogris.recap.R
import com.puntogris.recap.databinding.MainBottomNavigationDrawerBinding
import com.puntogris.recap.databinding.MainBottomNavigationDrawerProfileContentBinding
import com.puntogris.recap.databinding.MainBottomNavigationDrawerProfileHeaderBinding
import com.puntogris.recap.core.presentation.base.BaseBindingBottomSheetFragment
import com.puntogris.recap.core.utils.SimpleResult
import com.puntogris.recap.core.utils.loadProfilePicture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeBottomNavigationDrawer :
    BaseBindingBottomSheetFragment<MainBottomNavigationDrawerBinding>(R.layout.main_bottom_navigation_drawer) {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val contentBinding:
            MainBottomNavigationDrawerProfileContentBinding by viewBinding(R.id.bottom_navigation_content)

    private val headerBinding:
            MainBottomNavigationDrawerProfileHeaderBinding by viewBinding(R.id.bottom_navigation_header)

    override fun onViewCreated() {

        contentBinding.headerNavigationView.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
        }
        binding.drawerNavigationView.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
        }

        with(viewModel) {
            authorizedLiveData.observe(viewLifecycleOwner) {
                with(contentBinding.headerNavigationView.menu) {
                    setGroupVisible(R.id.group_unauthorized, !it)
                    setGroupVisible(R.id.group_authorized, it)
                }
            }
            usernameLiveData.observe(viewLifecycleOwner) {
                headerBinding.headerTitle.text = it ?: getString(R.string.app_name)
            }
            emailLiveData.observe(viewLifecycleOwner) {
                headerBinding.headerSubtitle.text = it ?: "Open source recap platform for Android"
            }
            profilePictureLiveData.observe(viewLifecycleOwner) { url ->
                url?.let {
                    headerBinding.headerImageView.loadProfilePicture(it)
                } ?: run {
                    headerBinding.headerImageView.setImageResource(R.mipmap.ic_launcher_round)
                }
            }
        }
    }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_add_account -> findNavController().navigate(R.id.loginFragment)
            R.id.action_view_profile -> {
                val action = NavigationDirections.actionGlobalUserFragment(viewModel.userId.value)
                findNavController().navigate(action)
            }
            R.id.action_edit_profile -> findNavController().navigate(R.id.editProfileFragment)
            R.id.action_log_out -> handleLogOut()
            R.id.action_settings -> findNavController().navigate(R.id.settingsFragment)
            R.id.action_about -> findNavController().navigate(R.id.aboutFragment)
        }
        dismiss()
        return true
    }

    private fun handleLogOut() {
        lifecycleScope.launch {
            when (viewModel.logOut()) {
                SimpleResult.Failure -> {
                }
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
