package com.theapache64.twinkill.utils.extensions

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Binds the given layout to the calling activity
 */
fun <B : ViewDataBinding> Activity.bindContentView(@LayoutRes layoutId: Int): B {
    return DataBindingUtil.setContentView(this, layoutId)
}