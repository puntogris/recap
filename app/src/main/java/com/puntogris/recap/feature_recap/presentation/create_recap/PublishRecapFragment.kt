package com.puntogris.recap.feature_recap.presentation.create_recap

import android.content.Intent
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.core.utils.SimpleResource
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
            with(binding){
                when(val result = viewModel.publishRecap()){
                    is Resource.Error -> {
                        title.text = "Error"
                        summary.text = "An error occurred"
                        animationView.playAnimationOnce(R.raw.error)
                        continueButton.isEnabled = true
                    }
                    is Resource.Success -> {
                        title.text = "Success"
                        summary.text = "Share it with friends and get it approved faster!"
                        animationView.playAnimationOnce(R.raw.success)
                        continueButton.isEnabled = true
                        floatingActionButton2.show()
                        floatingActionButton2.setOnClickListener {
                            shareRecap(result.data)
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

    fun shareRecap(link: String) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, "Share this recap!")
            putExtra(Intent.EXTRA_TEXT, link)
        }, null)
        startActivity(share)
    }
}