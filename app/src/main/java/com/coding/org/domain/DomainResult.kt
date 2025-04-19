package com.coding.org.domain

sealed interface DomainResult<out T> {
    data class Success<out T>(val data: T) : DomainResult<T>
    data class Error(val message: String) : DomainResult<Nothing>
}

sealed class DomainError {
    object NetworkError : DomainError()
    object Unauthorized : DomainError()
    object NotFound : DomainError()
    object MalformedData : DomainError()
    data class UnknownError(val message: String) : DomainError()
}