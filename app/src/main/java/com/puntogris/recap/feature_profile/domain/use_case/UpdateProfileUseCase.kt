package com.puntogris.recap.feature_profile.domain.use_case

import com.lyft.kronos.KronosClock
import com.puntogris.recap.R
import com.puntogris.recap.core.utils.Constants
import com.puntogris.recap.core.utils.getTimestamp
import com.puntogris.recap.core.utils.toMs
import com.puntogris.recap.feature_profile.domain.model.UpdateProfileData
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository
import com.puntogris.recap.feature_profile.presentation.util.EditProfileResult

class UpdateProfileUseCase(
    private val profileRepository: ProfileRepository,
    private val kronosClock: KronosClock
) {

    suspend operator fun invoke(updateProfileData: UpdateProfileData): EditProfileResult {
        return updateProfileData.let {

            if (it.name.isBlank() || it.username.isBlank()) {
                return EditProfileResult.Error(R.string.name_username_required)
            }

            if (it.username.contains(" ")) {
                return EditProfileResult.Error(R.string.username_not_valid)
            }

            val timeNow = kronosClock.getCurrentTime().posixTimeMs
            val timeSinceLastEdit = timeNow - updateProfileData.lastEdited.toMs()

            if (timeSinceLastEdit < Constants.TIME_EDIT_LOCKED_IN_MS) {
                return EditProfileResult.EditLimit(
                    Constants.TIME_EDIT_LOCKED_IN_SEC - timeSinceLastEdit / 1000
                )
            }

            updateProfileData.lastEdited = kronosClock.getTimestamp()

            profileRepository.updateUserProfile(it)
        }
    }
}