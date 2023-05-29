package com.xdlamni.weatherforecast.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class SafeApiCall {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiToBeCalled()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        ApiResponse.Success(data)
                    } else {
                        ApiResponse.Error(204, "No Content")
                    }
                } else {
                    val errorBody = response.errorBody()
                    val errorMessage = errorBody?.string() ?: "Unknown error"
                    ApiResponse.Error(response.code(), errorMessage)
                }
            } catch (e: HttpException) {
                ApiResponse.Error(500, e.message ?: "Something went wrong")
            } catch (e: IOException) {
                ApiResponse.Error(503,"Please check your network connection")
            } catch (e: Exception) {
                ApiResponse.Error(400,"Something went wrong")
            }
        }
    }
}