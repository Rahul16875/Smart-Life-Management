package com.example.smartlifemanager.domain.usecase

sealed class UseCaseResult<out T> {
    data class Success<T>(val data: T) : UseCaseResult<T>()
    data class Error(val message: String) : UseCaseResult<Nothing>()
}
