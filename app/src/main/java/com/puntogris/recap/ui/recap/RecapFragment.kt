package com.puntogris.recap.ui.recap

import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecapFragment : BaseFragment<FragmentRecapBinding>(R.layout.fragment_recap) {

    private val viewModel: RecapViewModel by viewModels()
    private val args: RecapFragmentArgs by navArgs()

    override fun initializeViews() {
        binding.fragment = this
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_report -> {
                    val action = RecapFragmentDirections.actionRecapFragmentToReportRecapBottomSheet(args.recap.id)
                    findNavController().navigate(action)
                }
            }
            true
        }
        binding.recapEditor.setEditorBackgroundColor(Color.BLACK)
        binding.recapEditor.setEditorFontColor(Color.WHITE)
    }

    fun onFavoriteClicked(){
        findNavController().navigate(R.id.loginFragment)
        if (viewModel.isUserLoggedIn()){

        }else{

        }
        showSnackBar("Agregado a favoritos")

    }

    fun onLikedClicked(){
        if (viewModel.isUserLoggedIn()){

        }else{

        }
    }

    fun onShareClicked(){
        if (viewModel.isUserLoggedIn()){

        }else{

        }
    }

}