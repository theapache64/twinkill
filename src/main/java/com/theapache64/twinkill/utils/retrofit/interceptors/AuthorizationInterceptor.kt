package com.theapache64.twinkill.utils.retrofit.interceptors

class AuthorizationInterceptor constructor(
    var apiKey: String?
) : BaseHeaderInterceptor() {
    override fun getKey(): String = "Authorization"
    override fun getValue(): String? = apiKey
}