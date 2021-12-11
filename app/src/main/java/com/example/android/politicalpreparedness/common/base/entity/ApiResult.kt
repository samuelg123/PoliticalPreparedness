package com.example.android.politicalpreparedness.common.base.entity


/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class ApiResult<out T> {
    data class Success<out T>(val value: T): ApiResult<T>()
    data class GenericError(val code: Int? = null, val error: Throwable? = null): ApiResult<Nothing>()
    data class NetworkError(val error: Throwable? = null): ApiResult<Nothing>()
}