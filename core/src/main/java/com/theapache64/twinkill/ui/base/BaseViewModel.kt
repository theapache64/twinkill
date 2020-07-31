package com.theapache64.twinkill.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.theapache64.twinkill.utils.livedata.SingleLiveEvent

open class BaseViewModel : ViewModel() {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val snackBarMessage = SingleLiveEvent<Any>()
    fun getSnackBarMessage(): LiveData<Any> = snackBarMessage

    @Suppress("MemberVisibilityCanBePrivate")
    protected val toastMessage = SingleLiveEvent<Any>()
    fun getToastMessage(): LiveData<Any> = toastMessage

}