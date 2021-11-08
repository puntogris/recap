package com.puntogris.recap.feature_recap.presentation.recap_detail

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.NavigationDirections
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.setupBackgroundAndFontColors
import com.puntogris.recap.core.utils.showSnackBar
import com.puntogris.recap.databinding.FragmentRecapBinding
import com.puntogris.recap.feature_recap.domain.model.isApproved
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecapFragment : BaseBindingFragment<FragmentRecapBinding>(R.layout.fragment_recap) {

    private val viewModel: RecapViewModel by viewModels()
    private val args: RecapFragmentArgs by navArgs()

    override fun initializeViews() {
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recapEditor.setupBackgroundAndFontColors()
        binding.recapEditor.setInputEnabled(false)

        subscribeRecapState()
        setupNavigation()
    }

    private fun subscribeRecapState() {
        viewModel.recapState.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {


                }
                is Resource.Success -> {
                    viewModel.updateRecap(it.data)

                    binding.recapHeader.viewStub?.apply {
                        layoutResource = if (it.data.isApproved()) {
                            R.layout.recap_detail_header
                        } else {
                            R.layout.rate_recap_header
                        }
                        inflate()
                    }
                }
            }
        }
    }

    private fun setupNavigation() {
        with(binding.toolbar) {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_report -> {
                        val id = args.recap?.id ?: args.recapId!!
                        val action =
                            RecapFragmentDirections.actionRecapFragmentToReportRecapBottomSheet(id)
                        findNavController().navigate(action)
                    }
                }
                true
            }
        }
    }

    private fun navigateToProfile() {
        val action = NavigationDirections.actionGlobalUserFragment(viewModel.recap.value?.author)
        findNavController().navigate(action)
    }

    fun onFavoriteClicked() {
        findNavController().navigate(R.id.loginFragment)
        if (viewModel.isUserLoggedIn()) {

        } else {

        }
        showSnackBar("Agregado a favoritos")

    }

    fun onLikedClicked() {
        if (viewModel.isUserLoggedIn()) {

        } else {

        }
    }

    fun onRateClicked() {
        val action =
            RecapFragmentDirections.actionRecapFragmentToSelectRatingDialog(viewModel.recap.value.id)
        findNavController().navigate(action)
    }

    fun onShareClicked() {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "Share this recap!")
            putExtra(Intent.EXTRA_TEXT, viewModel.recap.value.deepLink)
        }, null)
        startActivity(share)
    }

}