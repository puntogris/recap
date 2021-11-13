package com.puntogris.recap.feature_profile.presentation.edit_profile

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puntogris.recap.R
import com.puntogris.recap.core.presentation.base.BaseBindingFragment
import com.puntogris.recap.core.utils.*
import com.puntogris.recap.databinding.FragmentEditProfileBinding
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment :
    BaseBindingFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    private val args: EditProfileFragmentArgs by navArgs()
    private val viewModel: EditProfileViewModel by viewModels()
    private lateinit var photoLauncher: ActivityResultLauncher<String>

    override fun initializeViews() {
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.setUser(args.profile)

        registerToolbarBackButton(binding.toolbar)
        registerPhotoLauncher()
        registerFragmentListener()
    }

    private fun registerPhotoLauncher() {
        photoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { viewModel.updateProfileUri(it) }
        }
    }

    private fun registerFragmentListener() {
        setFragmentResultListener("photoOption") { _, bundle ->
            bundle.getParcelable<EditPhotoOptions>("data")?.let {
                when (it) {
                    EditPhotoOptions.Change -> photoLauncher.launch("image/*")
                    EditPhotoOptions.Delete -> viewModel.updateProfileUri(null)
                }
            }
        }
    }

    fun saveProfile() {
        isLoadingUi(true)
        updateProfile()

        lifecycleScope.launch {
            when (val result = viewModel.saveProfileChanges()) {
                is EditProfileResult.EditLimit -> {
                    binding.editProfileAlert.setTimeToUnlockProfileEdit(result.secondsToUnlock)
                    showSnackBar(R.string.snack_edit_profile_limit)
                }
                is EditProfileResult.Error -> showSnackBar(result.error)
                is EditProfileResult.Success -> showSnackBar(R.string.profile_updated_success)
            }
            isLoadingUi(false)
        }
    }

    private fun isLoadingUi(isLoading: Boolean) {
        binding.saveButton.isVisible = !isLoading
        binding.saveProgressBar.isVisible = isLoading
    }

    private fun updateProfile() {
        viewModel.updateProfileFields(
            name = binding.nameInput.getString(),
            username = binding.usernameInput.getString(),
            bio = binding.bioInput.getString()
        )
    }

    fun changeProfileImage() {
        findNavController().navigate(R.id.changeProfileImageBottomSheet)
    }
}