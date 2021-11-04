package com.puntogris.recap.feature_profile.domain.use_case

class ProfileUseCases(
    val getProfile: GetProfileUseCase,
    val updateProfile: UpdateProfileUseCase,
    val getRecaps: GetRecapsForProfileUseCase,
    val getDrafts: GetDraftsForProfileUseCase
)
