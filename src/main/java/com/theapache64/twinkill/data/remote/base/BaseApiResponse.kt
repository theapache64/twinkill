package com.theapache64.twinkill.data.remote.base

import com.squareup.moshi.Json

/**
 * Common base API response
 */
open class BaseApiResponse<T>(
    @Json(name = "error") val error: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "data") val data: T? = null
)