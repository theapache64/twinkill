package com.theapache64.twinkill.network.utils.retrofit.convertors

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Retrofit converter for String response
 */
class ToStringConverterFactory : Converter.Factory() {

    companion object {
        private val MEDIA_TYPE = "text/plain".toMediaTypeOrNull()
    }


    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return if (String::class.java == type) {
            Converter<ResponseBody, String> { value -> value.string() }
        } else null
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {

        return if (String::class.java == type) {
            Converter<String, RequestBody> { value -> value.toRequestBody(MEDIA_TYPE) }
        } else null
    }
}