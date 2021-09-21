package com.puntogris.recap.utils

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.puntogris.recap.R
import com.puntogris.recap.model.Recap
import com.puntogris.recap.model.RecapInteractions
import kotlinx.coroutines.flow.Flow

sealed class SimpleResult{
    object Success: SimpleResult()
    object Failure: SimpleResult()
}

sealed class EditProfileResult{
    object Success: EditProfileResult()
    sealed class Failure: EditProfileResult(){
        object NameLimit: Failure()
        object BioLimit: Failure()
        object PhotoLimit: Failure()
        object AccountIdLimit: Failure()
        object Error: Failure()
    }
}

sealed class LoginResult {
    object InProgress: LoginResult()
    class Success(): LoginResult()
    class Error(): LoginResult()
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

sealed class RecapInteractionResult {
    data class Success(val data: RecapInteractions) : RecapInteractionResult()
    data class Error(val exception: Exception) : RecapInteractionResult()
    object NotFound: RecapInteractionResult()
}


enum class RecapOrder(@StringRes val titleRes: Int, val value: String) {
    LATEST(R.string.order_latest, "latest"),
    OLDEST(R.string.order_oldest,"oldest"),
    POPULAR(R.string.order_popular, "popular")
}

enum class ReviewOrder(@StringRes val titleRes: Int, val value: String) {
    ALL(R.string.order_all, "all"),
}

sealed class RecapResult {
    object InProgress: RecapResult()
    class Success(val result: Flow<PagingData<Recap>>): RecapResult()
    object Error: RecapResult()
}