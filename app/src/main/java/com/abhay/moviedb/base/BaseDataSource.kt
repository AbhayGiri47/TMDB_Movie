package com.abhay.moviedb.base

import com.abhay.moviedb.util.Constants
import com.abhay.moviedb.util.NetworkUtils
import retrofit2.Response
import javax.inject.Inject

abstract class BaseDataSource (private val networkUtil: NetworkUtils) {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            if (networkUtil.isNetworkConnected) {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return Result.success(body)
                }
                return error(" ${response.code()} ${response.message()}")

            } else {
                return error(Constants.INTERNET_ERROR_MESSAGE)
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        return Result.error("Network call failed due to: $message")
    }

}

data class Result<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }
}