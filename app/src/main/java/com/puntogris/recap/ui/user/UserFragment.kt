package com.puntogris.recap.ui.user

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentUserBinding
import com.puntogris.recap.model.Recap
import com.puntogris.recap.ui.base.BaseViewPagerFragment
import com.puntogris.recap.utils.registerToolbarBackButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseViewPagerFragment<FragmentUserBinding>(R.layout.fragment_user) {

    override val viewPager: ViewPager2
        get() = binding.viewPager

    override val tabLayout: TabLayout
        get() = binding.tabLayout

    override val adapter: FragmentStateAdapter
        get() = UserSlidePagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, isProfileFromCurrentUser())

    override val tabsNames: Array<String>
        get() = resources.getStringArray(R.array.profile_tabs)

    override val viewModel: UserViewModel by viewModels()

    private val args: UserFragmentArgs by navArgs()

    override fun initializeViews() {
        super.initializeViews()
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupToolbar()

        with(viewModel){
            if (args.profile != null){
                setUser(args.profile!!)
                setUserId(args.profile!!.uid)
            }else {
                getUser(args.userId!!)
                setUserId(args.userId!!)
            }
        }

        binding.contentLoadingLayout.hide()
    }

    private fun setupToolbar(){
        with(binding.toolbar){
            registerToolbarBackButton(this)
            inflateMenu(R.menu.menu_current_user_profile)
            if (isProfileFromCurrentUser()){
                //show the edit button hide the report one
            }
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_edit_profile -> {
                        val action = UserFragmentDirections.actionUserFragmentToEditProfileFragment(viewModel.userProfile.value!!)
                        findNavController().navigate(action)
                    }
                }
                true
            }
        }
    }

    private fun isProfileFromCurrentUser(): Boolean{
        val userId = if (args.profile != null) args.profile!!.uid else args.userId!!
        return viewModel.isProfileFromCurrentUser(userId)
    }

    fun navigateToRecap(recap: Recap){
        val action = UserFragmentDirections.actionUserFragmentToRecapFragment(recap)
        findNavController().navigate(action)
    }

    fun navigateToCreateRecap(recap: Recap){
        val action = UserFragmentDirections.actionUserFragmentToCreateRecapGraph(recap)
        findNavController().navigate(action)
    }

}