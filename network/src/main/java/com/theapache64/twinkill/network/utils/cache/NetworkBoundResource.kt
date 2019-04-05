/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.theapache64.twinkill.network.utils.cache

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.theapache64.twinkill.utils.*

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<com.theapache64.twinkill.network.utils.Resource<ResultType>>()

    init {
        result.value = com.theapache64.twinkill.network.utils.Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            appExecutors.diskIO().execute {
                if (shouldFetch(data)) {
                    appExecutors.mainThread().execute {
                        fetchFromNetwork(dbSource)
                    }
                } else {
                    appExecutors.mainThread().execute {
                        result.addSource(dbSource) { newData ->
                            setValue(com.theapache64.twinkill.network.utils.Resource.success(newData))
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: com.theapache64.twinkill.network.utils.Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            setValue(com.theapache64.twinkill.network.utils.Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is com.theapache64.twinkill.network.utils.ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        appExecutors.mainThread().execute {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) { newData ->
                                setValue(com.theapache64.twinkill.network.utils.Resource.success(newData))
                            }
                        }
                    }
                }
                is com.theapache64.twinkill.network.utils.ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(com.theapache64.twinkill.network.utils.Resource.success(newData))
                        }
                    }
                }
                is com.theapache64.twinkill.network.utils.ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(com.theapache64.twinkill.network.utils.Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<com.theapache64.twinkill.network.utils.Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: com.theapache64.twinkill.network.utils.ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @WorkerThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<com.theapache64.twinkill.network.utils.ApiResponse<RequestType>>
}
