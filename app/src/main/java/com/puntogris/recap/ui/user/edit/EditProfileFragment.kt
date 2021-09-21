package com.puntogris.recap.ui.user.edit

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentEditProfileBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.SimpleResult
import com.puntogris.recap.utils.registerToolbarBackButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val args: EditProfileFragmentArgs by navArgs()
    private val viewModel: EditProfileViewModel by viewModels()

    override fun initializeViews() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.setUser(args.profile)

        registerToolbarBackButton(binding.toolbar)

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_save_profile -> saveProfile()
            }
            true
        }
    }

    private fun saveProfile(){
        lifecycleScope.launch {
            when(viewModel.saveProfileChanges()){
                SimpleResult.Failure -> {

                }
                SimpleResult.Success -> {

                }
            }
        }
    }

}