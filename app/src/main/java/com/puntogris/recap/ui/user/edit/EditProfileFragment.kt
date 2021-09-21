package com.puntogris.recap.ui.user.edit

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.databinding.FragmentEditProfileBinding
import com.puntogris.recap.ui.base.BaseFragment
import com.puntogris.recap.utils.*
import com.puntogris.recap.utils.EditProfileResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val args: EditProfileFragmentArgs by navArgs()
    private val viewModel: EditProfileViewModel by viewModels()
    private lateinit var getContent: ActivityResultLauncher<String>

    override fun initializeViews() {
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.setUser(args.profile)

        registerToolbarBackButton(binding.toolbar)

        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.updateProfileUri(it)
            }
        }

        getContent.launch("image/*")
    }

    fun saveProfile(){
        binding.saveButton.gone()
        binding.saveProgressBar.visible()

        lifecycleScope.launch {
            val result = viewModel.saveProfileChanges()

            binding.saveButton.visible()
            binding.saveProgressBar.gone()

            when(result){
                EditProfileResult.Failure.AccountIdLimit -> {

                }
                EditProfileResult.Failure.BioLimit -> {

                }
                EditProfileResult.Failure.Error -> {

                }
                EditProfileResult.Failure.NameLimit -> {

                }
                EditProfileResult.Failure.PhotoLimit -> {

                }
                EditProfileResult.Success -> {

                }
            }

        }
    }

    fun changeProfileImage(){
        findNavController().navigate(R.id.changeProfileImageBottomSheet)
    }

}