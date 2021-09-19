package com.puntogris.recap.ui.recap

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentRecapBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.Result
import com.puntogris.recap.utils.setupBackgroundAndFontColors
import com.puntogris.recap.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecapFragment : BaseFragment<FragmentRecapBinding>(R.layout.fragment_recap) {

    private val viewModel: RecapViewModel by viewModels()
    private val args: RecapFragmentArgs by navArgs()

    override fun initializeViews() {
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recapEditor.setupBackgroundAndFontColors()

        subscribeRecapState()
        setupNavigation()
    }

    private fun subscribeRecapState(){
        viewModel.recapState.observe(viewLifecycleOwner){
            when(it){
                is Result.Error -> {

                }
                is Result.Success -> {
                    viewModel.updateRecap(it.data)
                }
            }
        }
    }

    private fun setupNavigation(){
        with(binding.toolbar){
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_report -> {
                        val id = args.recap?.id ?: args.recapId!!
                        val action = RecapFragmentDirections.actionRecapFragmentToReportRecapBottomSheet(id)
                        findNavController().navigate(action)
                    }
                }
                true
            }
        }
    }

    private fun navigateToProfile(){
        val action = RecapFragmentDirections.actionRecapFragmentToUserFragment(viewModel.recap.value?.author)
        findNavController().navigate(action)
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
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "Share this recap!")
            putExtra(Intent.EXTRA_TEXT, viewModel.recap.value?.deepLink)
        }, null)
        startActivity(share)
    }

}