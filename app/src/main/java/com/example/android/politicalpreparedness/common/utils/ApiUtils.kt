package com.example.android.politicalpreparedness.common.utils

import com.example.android.politicalpreparedness.common.base.entity.ApiResult
import okio.IOException
import retrofit2.HttpException

object ApiUtils {
    suspend fun <T> wrapApiResponse(apiCall: suspend () -> T): ApiResult<T> = try {
        ApiResult.Success(apiCall.invoke())
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ApiResult.NetworkError(throwable)
            is HttpException -> ApiResult.GenericError(throwable.code(), throwable)
            else -> ApiResult.GenericError(null, throwable)
        }
    }
}