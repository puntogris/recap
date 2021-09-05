package com.puntogris.recap.ui.explore

import androidx.annotation.NonNull
import androidx.fragment.app.*
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentExploreBinding
import com.puntogris.recap.models.Recap
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.explore.recaps.ExploreRecapFragment
import com.puntogris.recap.ui.explore.reviews.ExploreReviewFragment
import com.puntogris.recap.utils.RecapOrder
import com.puntogris.recap.utils.ReviewOrder
import com.puntogris.recap.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(R.layout.fragment_explore) {

    private val viewModel: ExploreViewModel by activityViewModels()
    private var mediator: TabLayoutMediator? = null

    override fun initializeViews() {
        binding.fragment = this
        setupBottomAppBar()
        setupExplorePager()

//        repeat(10){
//            val recap = Recap(
//                title = "the office $it",
//                rating = (1..10).random()
//            )
//            Firebase.firestore.collection("recaps").add(recap)
//        }

    }

    private fun setupBottomAppBar(){
        binding.bottomAppBar.apply {
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_search -> {
                        findNavController().navigate(R.id.searchFragment)
                        true
                    }
                    R.id.action_order -> {
                        when (binding.tabLayout.selectedTabPosition) {
                            0 -> findNavController().navigate(R.id.recapOrderDialog)
                            1 -> findNavController().navigate(R.id.reviewOrderDialog)
                        }
                        true
                    }
                    else -> true
                }
            }
            setNavigationOnClickListener {
                findNavController().navigate(R.id.mainBottomNavigationDrawer)
            }
        }
    }

    private fun setupExplorePager(){
        binding.viewPager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Explorar"
                else -> "Calificar"
            }
        }
        mediator?.attach()
    }

    private inner class ScreenSlidePagerAdapter(@NonNull parentFragment: FragmentManager) : FragmentStateAdapter(parentFragment, viewLifecycleOwner.lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment =
            (if (position == 0 ) ExploreRecapFragment() else ExploreReviewFragment()).apply {

            }
    }

    fun navigateToCreateRecap(){
        findNavController().navigate(R.id.createRecapFragment)
    }

    fun showFavoriteSnack(){
        showSnackBar("Agregado a favoritos", anchorView = binding.createFab)
    }

    override fun onDestroyView() {
        mediator?.detach()
        mediator = null
        binding.viewPager.adapter = null
        super.onDestroyView()
    }

}