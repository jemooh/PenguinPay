package com.jkirwa.penguinpay.feature_send_money.data.data_source.remote.model

/**
 * Sealed class for networking and UI states
 */
sealed class Result<out T : Any> {
    object Loading : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
