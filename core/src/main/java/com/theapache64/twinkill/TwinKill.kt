package com.theapache64.twinkill

import com.theapache64.twinkill.utils.Font
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import okhttp3.Interceptor
import retrofit2.CallAdapter

/**
 *  To control your app deps easily
 */
class TwinKill private constructor(
    val interceptors: List<Interceptor>,
    val callAdapterFactories: List<CallAdapter.Factory>,
    val isNeedDeepCheckOnNetworkResponse: Boolean,
    val isHttpLoggingInterceptorEnabled: Boolean
) {
    class Builder {

        private var isHttpLoggingInterceptorEnabled: Boolean = false
        private val okHttpInterceptors = mutableListOf<Interceptor>()
        private val callAdapterFactories = mutableListOf<CallAdapter.Factory>()
        private var isNeedDeepCheckOnNetworkResponse = false

        fun build(): TwinKill {
            return TwinKill(
                okHttpInterceptors,
                callAdapterFactories,
                isNeedDeepCheckOnNetworkResponse,
                isHttpLoggingInterceptorEnabled
            )
        }

        /**
         * To add new OkHttpInterceptor to the default NetworkModule
         */
        fun addOkHttpInterceptor(interceptor: Interceptor): Builder {
            this.okHttpInterceptors.add(interceptor)
            return this
        }

        /**
         * To add new retrofit call adapter to default NetworkModule
         */
        fun addCallAdapter(factory: CallAdapter.Factory): Builder {
            this.callAdapterFactories.add(factory)
            return this
        }

        /**
         * Set default font using Calligraphy
         */
        fun setDefaultFont(font: Font): Builder {
            setDefaultFont(font.path)
            return this
        }

        /**
         * When set to true, Resource adapter will check with the BaseApiResponse adapter's error flag.
         */
        fun setNeedDeepCheckOnNetworkResponse(isNeedDeepCheckOnNetworkResponse: Boolean): Builder {
            this.isNeedDeepCheckOnNetworkResponse = isNeedDeepCheckOnNetworkResponse
            return this
        }

        /**
         * Sets primary color of the app
         */

        private fun setDefaultFont(path: String) {

            // Initializing  ViewPump
            ViewPump.init(
                ViewPump.builder()
                    .addInterceptor(
                        // Calligraphy configuration
                        CalligraphyInterceptor(
                            CalligraphyConfig.Builder()
                                .setDefaultFontPath(path)
                                .setFontAttrId(R.attr.fontPath)
                                .build()
                        )
                    )
                    .build()
            )
        }

        fun enableHttpLoggingInterceptor(): Builder {
            this.isHttpLoggingInterceptorEnabled = true
            return this;
        }
    }

    companion object {

        lateinit var INSTANCE: TwinKill

        fun init(orchid: TwinKill) {
            INSTANCE = orchid
        }

        fun builder(): Builder {
            return Builder()
        }

    }
}