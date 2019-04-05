package com.theapache64.twinkill.network.utils.retrofit.adapters.resourceadapter

import androidx.lifecycle.LiveData
import com.theapache64.twinkill.network.utils.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * To convert retrofit response to LiveData<Resource<T>>.
 * Inspired from LiveDataCallAdapterFactory
 */
class ResourceCallAdapter<R>(private val responseType: Type, private val isNeedDeepCheck: Boolean = false) :
    CallAdapter<R, LiveData<Resource<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<Resource<R>> {
        return object : LiveData<Resource<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()

                if (started.compareAndSet(false, true)) {

                    // Firing loading resource
                    postValue(Resource.loading())


                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(Resource.create(response, isNeedDeepCheck))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            throwable.printStackTrace()
                            postValue(Resource.create(throwable))
                        }
                    })
                }
            }
        }
    }
}