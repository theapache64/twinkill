package com.theapache64.twinkill

import com.theapache64.twinkill.utils.Font
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import okhttp3.Interceptor

/**
 *  To control your app deps easily
 */
class TwinKill private constructor(
    val interceptors: List<Interceptor>
) {

    class Builder {

        private val okHttpInterceptors = mutableListOf<Interceptor>()

        fun build(): TwinKill {
            return TwinKill(okHttpInterceptors)
        }

        /**
         * To add new OkHttpInterceptor to the default NetworkModule
         */
        fun addOkHttpInterceptor(interceptor: Interceptor): Builder {
            this.okHttpInterceptors.add(interceptor)
            return this
        }

        /**
         * Set default font using Calligraphy
         */
        fun setDefaultFont(font: Font): Builder {
            setDefaultFont(font.path)
            return this
        }

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