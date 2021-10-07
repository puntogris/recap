package com.puntogris.recap.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentLoginBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.ui.main.UiListener
import com.puntogris.recap.utils.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var loginActivityResultLauncher: ActivityResultLauncher<Intent>

    private lateinit var uiListener: UiListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        uiListener = context as UiListener
    }

    override fun initializeViews() {
        binding.fragment = this
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        registerActivityResultLauncher()
    }

    private fun registerActivityResultLauncher(){
        loginActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            binding.contentLoadingLayout.hide()
            if (it.resultCode == Activity.RESULT_OK) {
                handleLoginActivityResult(it.data)
                binding.contentLoadingLayout.show()
            }
            else if (it.resultCode == Activity.RESULT_CANCELED) {
                uiListener.showSnackBar(getString(R.string.snack_fail_login))
            }
        }
    }

    private fun handleLoginActivityResult(intent: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.getResult(ApiException::class.java)!!
            authUserIntoFirebase(account.idToken!!)
        } catch (e: ApiException) {
            uiListener.showSnackBar(getString(R.string.snack_fail_login))
        }
    }

    private fun authUserIntoFirebase(idToken: String){
        lifecycleScope.launch {
            viewModel.authUserWithFirebase(idToken).collect {
                handleAuthUserIntoFirebaseResult(it)
            }
        }
    }

    private fun handleAuthUserIntoFirebaseResult(result: LoginResult){
        when (result) {
            is LoginResult.Error -> {
                uiListener.showSnackBar(getString(R.string.snack_fail_login))
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
        val intent = viewModel.getGoogleSignInIntent()
        loginActivityResultLauncher.launch(intent)
    }

    fun onNavigateUp(){
        findNavController().navigateUp()
    }
}