package com.puntogris.recap.ui.user

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserBinding
import com.puntogris.recap.ui.base.BaseViewPagerFragment
import com.puntogris.recap.utils.registerToolbarBackButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseViewPagerFragment<FragmentUserBinding>(R.layout.fragment_user) {

    override val viewPager: ViewPager2
        get() = binding.viewPager

    override val tabLayout: TabLayout
        get() = binding.tabLayout

    override val tabsNames = listOf("Recaps", "Calificar")

    override val viewModel: UserViewModel by viewModels()

    override val adapter: FragmentStateAdapter
        get() = UserSlidePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

    private val args: UserFragmentArgs by navArgs()

    override fun initializeViews() {
        super.initializeViews()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        
        registerToolbarBackButton(binding.toolbar)

        if (args.profile != null){
            viewModel.setUser(args.profile!!)
            viewModel.setUserId(args.profile!!.uid)
        }else {
            viewModel.getUser(args.userId!!)
            viewModel.setUserId(args.userId!!)
        }

        binding.contentLoadingLayout.hide()
    }

}