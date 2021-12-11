package com.example.android.politicalpreparedness.common.base.entity

import kotlinx.coroutines.flow.*


/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class ResultWrapper<out T : Any?> {
    data class Success<T : Any?>(val data: T) : ResultWrapper<T>()
    data class Error(
        val message: String? = null,
        val statusCode: Int? = null,
        val error: Throwable? = null
    ) : ResultWrapper<Nothing>()
}