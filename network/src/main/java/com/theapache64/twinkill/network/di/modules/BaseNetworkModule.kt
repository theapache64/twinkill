package com.theapache64.twinkill.network.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.twinkill.TwinKill
import com.theapache64.twinkill.network.utils.retrofit.adapters.resourcedataadapter.ResourceCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Basic network module for dagger
 */
@Module(includes = [MoshiModule::class])
class BaseNetworkModule(private val baseUrl: String) {


    // Interceptor
    @Singleton
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor? {
        return if (TwinKill.INSTANCE.isHttpLoggingInterceptorEnabled) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            null
        }

    }

    // Client
    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (loggingInterceptor != null) {
            // adding logging interceptor
            builder.addInterceptor(loggingInterceptor)
        }

        // adding other interceptors
        TwinKill.INSTANCE.interceptors.forEach { builder.addInterceptor(it) }

        return builder
            .build()
    }

    // Retrofit
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        callAdapterFactories: Array<CallAdapter.Factory>
    ): Retrofit {


        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))

        callAdapterFactories.forEach {
            retrofitBuilder.addCallAdapterFactory(it)
        }

        TwinKill.INSTANCE.callAdapterFactories.forEach {
            retrofitBuilder.addCallAdapterFactory(it)
        }

        return retrofitBuilder
            .build()
    }

    @Singleton
    @Provides
    fun provideCallAdapters(): Array<CallAdapter.Factory> {
        val isNeedDeepCheck = TwinKill.INSTANCE.isNeedDeepCheckOnNetworkResponse

        return arrayOf(
            ResourceCallAdapterFactory(isNeedDeepCheck)
        )
    }

}