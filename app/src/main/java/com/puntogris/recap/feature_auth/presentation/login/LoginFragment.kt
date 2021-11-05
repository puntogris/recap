package com.puntogris.recap.feature_auth.presentation.login

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentLoginBinding
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.UiListener
import com.puntogris.recap.feature_auth.presentation.util.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private lateinit var googleSingInLauncher: ActivityResultLauncher<Intent>
    private lateinit var uiListener: UiListener
    private val viewModel: LoginViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        uiListener = context as UiListener
    }

    override fun initializeViews() {
        binding.fragment = this
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        registerActivityResultLauncher()
    }

    private fun registerActivityResultLauncher() {
        googleSingInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                authGoogleUserIntoServer(it)
            }
    }

    private fun authGoogleUserIntoServer(result: ActivityResult) {
        lifecycleScope.launch {
            viewModel.authGoogleUser(result).collect(::handleAuthUserIntoServerResult)
        }
    }

    private fun handleAuthUserIntoServerResult(result: LoginResult) {
        when (result) {
            is LoginResult.Error -> {
                uiListener.showSnackBar(getString(R.string.snack_fail_login))
                binding.contentLoadingLayout.hide()
            }
            LoginResult.InProgress -> {
                binding.contentLoadingLayout.show()
            }
            is LoginResult.Success -> {
                findNavController().navigateUp()
                uiListener.showSnackBar("Sesion iniciada correctamente.")
            }
        }
    }

    fun startLoginWithGoogle() {
        googleSingInLauncher.launch(viewModel.getGoogleSignInIntent())
    }

    fun onNavigateUp() {
        findNavController().navigateUp()
    }
}