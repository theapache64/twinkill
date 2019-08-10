package com.theapache64.twinkill.utils

class Resource<out T> private constructor(
    val status: Status,
    val data: T?,
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
    }
}

