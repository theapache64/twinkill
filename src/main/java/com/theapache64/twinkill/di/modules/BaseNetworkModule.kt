package com.theapache64.twinkill.di.modules

import com.theapache64.twinkill.Orchid
import com.theapache64.twinkill.utils.retrofit.adapters.livedataadapter.LiveDataCallAdapterFactory
import com.theapache64.twinkill.utils.retrofit.adapters.resourceadapter.ResourceCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger
 */
@Module
class BaseNetworkModule(private val baseUrl: String) {

    // Interceptor
    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // Client
    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

        // adding logging interceptor
        builder.addInterceptor(interceptor)

        // adding other interceptors
        Orchid.INSTANCE.interceptors.forEach { builder.addInterceptor(it) }

        return builder
            .build()
    }

    // Retrofit
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(ResourceCallAdapterFactory())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

}