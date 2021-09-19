package com.puntogris.recap.ui.user

import androidx.annotation.NonNull
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.search.recap.SearchRecapFragment
import com.puntogris.recap.ui.search.user.SearchUserFragment
import com.puntogris.recap.utils.registerToolbarBackButton
import com.puntogris.recap.utils.setVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {

    private val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        registerToolbarBackButton(binding.toolbar)

        binding.contentLoadingLayout.hide()

        if (args.profile != null){
            viewModel.setUser(args.profile!!)
            viewModel.setUserId(args.profile!!.uid)
        }else {
            viewModel.getUser(args.userId!!)
            viewModel.setUserId(args.userId!!)
        }

        setupViewPager()
    }

    private fun setupViewPager(){
        binding.viewPager.apply {
            adapter = ScreenSlidePagerAdapter(childFragmentManager)
        }

        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Recaps"
                else -> "Reviews"
            }
        }
        mediator?.attach()
    }

    private inner class ScreenSlidePagerAdapter(@NonNull parentFragment: FragmentManager):
        FragmentStateAdapter(parentFragment, viewLifecycleOwner.lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int) =
            if (position == 0) UserRecapsFragment() else UserReviewsFragment()
    }


    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }
}