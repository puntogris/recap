package com.puntogris.recap.feature_profile.domain.use_case

import androidx.paging.PagingData
import androidx.paging.map
import com.puntogris.recap.feature_profile.domain.repository.ProfileRepository
import com.puntogris.recap.feature_recap.domain.model.Recap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRecapsForProfileUseCase(
    private val repository: ProfileRepository
) {
    operator fun invoke(recapStatus: String): Flow<PagingData<Recap>> {
        return repository.getRecapsPaged(recapStatus)
    }
}