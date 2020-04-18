package com.theapache64.twinkill.network.utils

import com.theapache64.twinkill.network.data.remote.base.BaseApiResponse
import org.json.JSONObject
import retrofit2.Response

class Resource<out T> private constructor(
    val status: Status, val data: T?,
    val message: String?
) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg
            )
        }


        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> create(status: Status, data: T?, message: String?): Resource<T> {
            return Resource(status, data, message)
        }

        /**
         * Create error resource
         */
        fun <T> create(throwable: Throwable): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                throwable.message ?: "unknown error"
            )
        }

        fun <T> create(response: Response<T>, isNeedDeepCheck: Boolean = false): Resource<T> {

            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    Resource<T>(
                        Status.SUCCESS,
                        null,
                        "No content"
                    )
                } else {
                    if (isNeedDeepCheck) {
                        if (body is BaseApiResponse<*>) {
                            val baseApiResponse = body as BaseApiResponse<*>
                            if (baseApiResponse.error) {
                                Resource<T>(
                                    Status.ERROR,
                                    body,
                                    baseApiResponse.message
                                )
                            } else {
                                Resource<T>(
                                    Status.SUCCESS,
                                    body,
                                    "success"
                                )
                            }
                        } else {
                            Resource<T>(
                                Status.ERROR,
                                body,
                                "Body doesn't follow BaseApiResponse standard. DeepCheck not possible"
                            )
                        }

                    } else {
                        Resource(
                            Status.SUCCESS,
                            body,
                            "success"
                        )
                    }
                }
            } else {
                val msg = response.errorBody()?.string()

                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    // Return error string
                    JSONObject(msg).getString("message")
                }
                error(errorMsg ?: "unknown error")
            }
        }
    }
}