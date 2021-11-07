package com.puntogris.recap.core.utils

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.puntogris.recap.R
import com.puntogris.recap.feature_recap.domain.model.Recap
import com.puntogris.recap.feature_recap.domain.model.RecapInteractions
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.Parcelize

sealed class RecapInteractionResult {
    data class Success(val data: RecapInteractions) : RecapInteractionResult()
    data class Error(val exception: Exception) : RecapInteractionResult()
    object NotFound : RecapInteractionResult()
}


sealed class RecapResult {
    object InProgress : RecapResult()
    class Success(val result: Flow<PagingData<Recap>>) : RecapResult()
    object Error : RecapResult()
}

@Parcelize
enum class EditPhotoOptions(val optionRes: String) : Parcelable {
    Change("Nueva foto de perfil"),
    Delete("Eliminar foto de perfil")
}
