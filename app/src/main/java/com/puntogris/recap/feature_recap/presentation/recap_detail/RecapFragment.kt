package com.puntogris.recap.feature_recap.presentation.recap_detail

import android.content.Intent
import android.text.Html
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.NavigationDirections
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.actionOrLogin
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
                    R.id.action_report -> actionOrLogin(viewModel.isUserLoggedIn()) {
                        val recapId = args.recap?.id ?: args.recapId!!
                        val action =
                            RecapFragmentDirections.actionRecapFragmentToReportRecapDialog(recapId)
                        findNavController().navigate(action)
                    }
                }
                true
            }
        }
    }

    private fun navigateToProfile() {
        val action = NavigationDirections.actionGlobalUserFragment(viewModel.recap.value.uid)
        findNavController().navigate(action)
    }

    fun onFavoriteClicked() {
        actionOrLogin(viewModel.isUserLoggedIn()) {
            showSnackBar("Agregado a favoritos")
        }
    }

    fun onLikedClicked() {
        actionOrLogin(viewModel.isUserLoggedIn()) {
            showSnackBar("Te gusto este recap.")
        }
    }

    fun onRateClicked() {
        actionOrLogin(viewModel.isUserLoggedIn()) {
            val action =
                RecapFragmentDirections.actionRecapFragmentToSelectRatingDialog(viewModel.recap.value.id)
            findNavController().navigate(action)
        }
    }

    fun onShareClicked() {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, getString(R.string.share_recap_intent_title))
            putExtra(Intent.EXTRA_TEXT, viewModel.recap.value.deepLink)
        }, null)
        startActivity(share)
    }
}