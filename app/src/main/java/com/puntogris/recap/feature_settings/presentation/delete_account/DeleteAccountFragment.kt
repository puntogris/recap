package com.puntogris.recap.feature_settings.presentation.delete_account

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.*
import com.puntogris.recap.databinding.FragmentDeleteAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeleteAccountFragment :
    BaseBindingFragment<FragmentDeleteAccountBinding>(R.layout.fragment_delete_account) {

    private val viewModel: DeleteAccountViewModel by viewModels()

    override fun initializeViews() {
        binding.fragment = this
        registerToolbarBackButton(binding.toolbar)
    }

    fun deleteAccount() {
        showLoadingProgress()

        lifecycleScope.launch {
            when (viewModel.deleteAccount(binding.emailField.getString())) {
                is SimpleResource.Error -> onDeleteFailure()
                is SimpleResource.Success -> onDeleteSuccess()
            }
        }
    }

    private fun showLoadingProgress() {
        with(binding) {
            animationView.visible()
            animationView.setAnimation(R.raw.loading)
            animationView.repeatCount = LottieDrawable.INFINITE
            animationView.playAnimation()
            emailField.gone()
        }
    }


    private fun onDeleteFailure() {
        with(binding) {
            animationView.gone()
            animationView.playAnimationOnce(R.raw.error)
            emailField.visible()
        }
        showSnackBar(getString(R.string.general_connection_error_message))
    }

    private fun onDeleteSuccess() {
        showSnackBar(getString(R.string.snack_account_deleted_success))
        val nav = NavOptions.Builder().setPopUpTo(R.id.navigation, true).build()
        findNavController().navigate(R.id.homeFragment, null, nav)
    }
}