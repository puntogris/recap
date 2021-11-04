package com.puntogris.recap.utils

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.puntogris.recap.R
import com.puntogris.recap.model.Recap
import com.puntogris.recap.model.RecapInteractions
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.Parcelize

sealed class SimpleResult {
    object Success : SimpleResult()
    object Failure : SimpleResult()

    companion object Factory {
        inline fun build(function: () -> Unit): SimpleResult =
            try {
                function.invoke()
                Success
            } catch (e: Exception) {
                Failure
            }
    }
}

sealed class EditProfileResult {
    object Success : EditProfileResult()
    sealed class Failure : EditProfileResult() {
        object NameLimit : Failure()
        object BioLimit : Failure()
        object PhotoLimit : Failure()
        object AccountIdLimit : Failure()
        object Error : Failure()
    }
}

sealed class LoginResult {
    object InProgress : LoginResult()
    object Success : LoginResult()
    object Error : LoginResult()
}

sealed class Result<out E : Exception, out V> {
    data class Success<out V>(val value: V) : Result<Nothing, V>()
    data class Error<out E : Exception>(val exception: Exception) : Result<E, Nothing>()

    companion object Factory {
        inline fun <V> build(function: () -> V): Result<Exception, V> =
            try {
                Success(function.invoke())
            } catch (e: Exception) {
                Error(e)
            }
    }
}

sealed class RecapInteractionResult {
    data class Success(val data: RecapInteractions) : RecapInteractionResult()
    data class Error(val exception: Exception) : RecapInteractionResult()
    object NotFound : RecapInteractionResult()
}

enum class RecapOrder(@StringRes val titleRes: Int, val value: String) {
    LATEST(R.string.order_latest, "latest"),
    OLDEST(R.string.order_oldest, "oldest"),
    POPULAR(R.string.order_popular, "popular")
}

enum class ReviewOrder(@StringRes val titleRes: Int, val value: String) {
    ALL(R.string.order_all, "all"),
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
