package com.theapache64.twinkill.network.utils.retrofit.adapters.flow

/**
 * Created by theapache64 : Jul 26 Sun,2020 @ 13:22
 */
sealed class Resource<T> {

    class Loading<T>(
        val data: T? = null
    ) : Resource<T>()

    data class Success<T>(
        val message: String?,
        val data: T
    ) : Resource<T>()

    data class Error<T>(
        val code: Int,
        val message: String
    ) : Resource<T>()
}
