package com.starz.play.coding.data.contract

import com.starz.play.coding.data.util.AppException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

abstract class RemoteCallExecutor {

    suspend fun <T : Any> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(AppException(getFriendlyMessage("empty_body")))
                }
            } else {
                Result.failure(
                    AppException(
                        message = getFriendlyMessage(response.code()),
                    )
                )
            }

        } catch (e: UnknownHostException) {
            Result.failure(AppException(getFriendlyMessage(e), original = e))
        } catch (e: IOException) {
            Result.failure(AppException(getFriendlyMessage(e), original = e))
        } catch (e: Exception) {
            Result.failure(AppException(getFriendlyMessage(e), original = e))
        }
    }

    private fun getFriendlyMessage(code: Int): String = when (code) {
        401 -> "Unauthorized request"
        403 -> "Access denied"
        404 -> "Data not found"
        in 500..599 -> "Server is currently unavailable"
        else -> "Something went wrong. Please try again."
    }

    private fun getFriendlyMessage(e: Throwable): String = when (e) {
        is UnknownHostException -> "No internet connection"
        is IOException -> "Network error. Please try again."
        else -> "Unexpected error occurred"
    }

    private fun getFriendlyMessage(message: String): String = when (message) {
        "empty_body" -> "Something went wrong. Please try again."
        else -> "Unexpected error"
    }
}
