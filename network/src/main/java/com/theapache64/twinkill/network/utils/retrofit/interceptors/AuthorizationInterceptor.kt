package com.theapache64.twinkill.network.utils.retrofit.interceptors

class AuthorizationInterceptor constructor(
    var apiKey: String?
) : BaseHeaderInterceptor() {
    override fun getKey(): String = "Authorization"
    override fun getValue(): String? = apiKey
}