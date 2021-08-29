package com.puntogris.recap.ui.login

import android.app.Activity
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
import com.puntogris.recap.utils.LoginResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var loginActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun initializeViews() {
        binding.fragment = this
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        registerActivityResultLauncher()
    }

    private fun registerActivityResultLauncher(){
        loginActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
           // binding.progressBar.gone()
            if (it.resultCode == Activity.RESULT_OK)
                handleLoginActivityResult(it.data)
            else if (it.resultCode == Activity.RESULT_CANCELED) {
          //      UiInterface.showSnackBar(getString(R.string.snack_fail_login), anchorToBottomNav = false)
            }
        }
    }

    private fun handleLoginActivityResult(intent: Intent?) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.getResult(ApiException::class.java)!!
            authUserIntoFirebase(account.idToken!!)
        } catch (e: ApiException) {
       //     UiInterface.showSnackBar(
      //          getString(R.string.snack_fail_login),
     //           anchorToBottomNav = false
      //      )
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
              //  UiInterface.showSnackBar(getString(R.string.snack_fail_login), anchorToBottomNav = false)
          //      binding.progressBar.gone()
            }
            LoginResult.InProgress -> {
            //    binding.progressBar.visible()
            }
            is LoginResult.Success -> {
             //   val action =
             //       LoginFragmentDirections.actionLoginFragmentToSynAccountFragment(result.userPrivateData)
            //    findNavController().navigate(action)
            }
        }
    }

    fun startLoginWithGoogle() {
     //   binding.progressBar.visible()
   //     binding.loginButton.playShakeAnimation()
        val intent = viewModel.getGoogleSignInIntent()
        loginActivityResultLauncher.launch(intent)
    }

    fun onNavigateUp(){
        findNavController().navigateUp()
    }
}