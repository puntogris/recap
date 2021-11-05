package com.puntogris.recap.feature_profile.domain.use_case

import com.puntogris.recap.core.utils.Resource
import com.puntogris.recap.feature_profile.domain.model.PublicProfile
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository

class GetProfileUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(userId: String): Resource<PublicProfile> {
        return profileRepository.getPublicProfile(userId)
    }
}