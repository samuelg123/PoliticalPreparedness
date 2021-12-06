package com.example.android.politicalpreparedness.common.base.entity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class ViewState<out T> {
    object Initial : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
    data class Success<out T>(val value: T) : ViewState<T>()
    data class Error(
        val message: String? = null,
        val error: Throwable? = null
    ) : ViewState<Nothing>()
}

class ViewStateWrapper<T> {
    var state: ViewState<T> = ViewState.Initial
}

fun <T : Any> Flow<ResultWrapper<T>>.toViewState(): Flow<ViewState<T?>> = this.map {
    when (it) {
        is ResultWrapper.Success -> ViewState.Success(it.data)
        is ResultWrapper.Error -> ViewState.Error(it.message, it.error)
    }
}
