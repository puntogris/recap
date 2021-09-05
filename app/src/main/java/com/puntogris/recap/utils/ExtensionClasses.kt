package com.puntogris.recap.utils

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.puntogris.recap.R
import com.puntogris.recap.models.Recap
import kotlinx.coroutines.flow.Flow

sealed class SimpleResult{
    object Success: SimpleResult()
    object Failure: SimpleResult()
}

sealed class LoginResult {
    object InProgress: LoginResult()
    class Success(): LoginResult()
    class Error(): LoginResult()
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