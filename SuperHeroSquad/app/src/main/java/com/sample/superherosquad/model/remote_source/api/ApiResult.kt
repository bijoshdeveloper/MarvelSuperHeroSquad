package com.sample.superherosquad.model.remote_source.api

/**
 * Sealed class for managing API response.
 **/
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val errorMessage: String): ApiResult<Nothing>()
}