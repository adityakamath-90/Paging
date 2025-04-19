package com.coding.org.data.remote

sealed interface DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>
    data class Failure(val error: DataError) : DataResult<Nothing>
}

sealed class DataError : Throwable() {
    object Network : DataError()
    object Timeout : DataError()
    object Unauthorized : DataError()
    object NotFound : DataError()
    object MalformedData : DataError()
    data class Unknown(val exception: Throwable) : DataError()
}