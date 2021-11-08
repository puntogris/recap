package com.puntogris.recap.feature_recap.presentation.create_recap

import android.content.Intent
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.launchAndRepeatWithViewLifecycle
import com.puntogris.recap.core.utils.playAnimationOnce
import com.puntogris.recap.databinding.FragmentPublishRecapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublishRecapFragment :
    BaseBindingFragment<FragmentPublishRecapBinding>(R.layout.fragment_publish_recap) {

    private val viewModel: CreateRecapViewModel by navGraphViewModels(R.id.createRecapGraph) { defaultViewModelProviderFactory }

    override fun initializeViews() {
        binding.fragment = this

        launchAndRepeatWithViewLifecycle {
            with(binding) {
                when (val result = viewModel.publishRecap()) {
                    is Resource.Error -> {
                        title.setText(R.string.general_error_message)
                        summary.setText(R.string.general_connection_error_message)
                        animationView.playAnimationOnce(R.raw.error)
                        continueButton.isEnabled = true
                    }
                    is Resource.Success -> {
                        title.setText(R.string.general_success_message)
                        summary.setText(R.string.publish_recap_success_summary)
                        animationView.playAnimationOnce(R.raw.success)
                        continueButton.isEnabled = true
                        shareButton.show()
                        shareButton.setOnClickListener {
                            shareRecap(result.data.link)
                        }
                    }
                }
            }
        }
    }

    fun onContinueButtonClicked() {
        val nav = NavOptions.Builder().setPopUpTo(R.id.navigation, true).build()
        findNavController().navigate(R.id.homeFragment, null, nav)
    }

    private fun shareRecap(link: String) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, getString(R.string.share_recap_intent_title))
            putExtra(Intent.EXTRA_TEXT, link)
        }, null)
        startActivity(share)
    }
}