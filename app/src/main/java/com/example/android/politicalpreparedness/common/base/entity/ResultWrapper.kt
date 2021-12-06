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

//fun <T : Any> ResultWrapper<T>.toStateFlow(process: suspend () -> T): Flow<ViewState<T>> = flow {
//    //emit loading //todo tomorrow
//
//    emit(ViewState.Initial)
//    process.invoke()
//    //emit success / error
//}
//
//fun <T : Any> Flow<ResultWrapper<T>>.toStateFlow() =
//    flow<T> {
//        emit(ViewState.Loading)
//        when(val v = this@toStateFlow.firstOrNull()){
//            is ResultWrapper.Success -> emit(ViewState.Success(v.data))
//            is ResultWrapper.Error -> emit(ViewState.Error())
//            null -> emit(ViewState.Error())
//        }
//    }
//
//fun <T> wrapFlow(process: suspend () -> T) {
//
//}