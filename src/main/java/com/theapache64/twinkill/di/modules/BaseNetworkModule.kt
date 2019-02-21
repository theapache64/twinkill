package com.theapache64.twinkill.di.modules

import com.squareup.moshi.Moshi
import com.theapache64.twinkill.TwinKill
import com.theapache64.twinkill.utils.retrofit.adapters.livedataadapter.LiveDataCallAdapterFactory
import com.theapache64.twinkill.utils.retrofit.adapters.resourceadapter.ResourceCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Dagger
 */
@Module(includes = [MoshiModule::class])
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
        TwinKill.INSTANCE.interceptors.forEach { builder.addInterceptor(it) }

        return builder
            .build()
    }

    // Retrofit
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {


        return Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(ResourceCallAdapterFactory())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

}