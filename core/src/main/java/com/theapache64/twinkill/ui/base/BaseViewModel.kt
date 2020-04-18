package com.theapache64.twinkill.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    protected val snackBarMessage = SingleLiveEvent<Any>()
    fun getSnackBarMessage(): LiveData<Any> = snackBarMessage

    protected val toastMessage = SingleLiveEvent<Any>()
    fun getToastMessage(): LiveData<Any> = toastMessage

}