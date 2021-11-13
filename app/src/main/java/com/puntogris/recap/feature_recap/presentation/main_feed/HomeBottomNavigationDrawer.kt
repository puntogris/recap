package com.puntogris.recap.feature_recap.presentation.main_feed

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.internal.NavigationMenuView
import com.puntogris.recap.NavigationDirections
import com.puntogris.recap.R
import com.puntogris.recap.core.utils.SimpleResource
import com.puntogris.recap.core.utils.loadProfilePicture
import com.puntogris.recap.databinding.MainBottomNavigationDrawerBinding
import com.puntogris.recap.databinding.MainBottomNavigationDrawerProfileContentBinding
import com.puntogris.recap.databinding.MainBottomNavigationDrawerProfileHeaderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeBottomNavigationDrawer : BottomSheetDialogFragment() {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private val drawerBinding:
            MainBottomNavigationDrawerBinding by viewBinding(CreateMethod.INFLATE)

    private val contentBinding:
            MainBottomNavigationDrawerProfileContentBinding by viewBinding(R.id.bottom_navigation_content)

    private val headerBinding:
            MainBottomNavigationDrawerProfileHeaderBinding by viewBinding(R.id.bottom_navigation_header)

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = drawerBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (contentBinding.headerNavigationView.getChildAt(0) as? NavigationMenuView)
            ?.isVerticalScrollBarEnabled = false
        drawerBinding.expandableProfile.setOnExpandChangeListener { isExpanded ->
            val drawableRes =
                if (isExpanded) R.drawable.ic_baseline_expand_less_18 else R.drawable.ic_baseline_expand_more_18
            headerBinding.headerSubtitle
                .setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawableRes, 0)
        }

        contentBinding.headerNavigationView.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
        }
        drawerBinding.drawerNavigationView.setNavigationItemSelectedListener {
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
                headerBinding.headerSubtitle.text = it ?: getString(R.string.header_subtitle)
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
                is SimpleResource.Error -> {
                }
                SimpleResource.Success -> {
                    dismiss()
                    setFragmentResult("home_fragment", bundleOf("log_out_result" to true))
                }
            }
        }
    }

    companion object {
        val TAG: String = HomeBottomNavigationDrawer::class.java.simpleName
        fun newInstance() = HomeBottomNavigationDrawer()
    }
}
