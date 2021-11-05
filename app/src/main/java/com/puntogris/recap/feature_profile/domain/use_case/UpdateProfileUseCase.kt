package com.puntogris.recap.feature_profile.domain.use_case

import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult

class UpdateProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(updateProfileData: UpdateProfileData): EditProfileResult {
        return profileRepository.updateUserProfile(updateProfileData)
    }
}