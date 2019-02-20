package com.theapache64.twinkill.data.remote.base

import com.google.gson.annotations.SerializedName

/**
 * Common base API response
 */
open class BaseApiResponse<T>(
    @SerializedName("error") val error: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: T
)