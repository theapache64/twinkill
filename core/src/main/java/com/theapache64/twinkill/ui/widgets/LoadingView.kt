package com.theapache64.twinkill.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.theapache64.twinkill.databinding.LoadingViewBinding

class LoadingView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val binding: LoadingViewBinding

    init {
        val inflater = LayoutInflater.from(getContext())
        this.binding = LoadingViewBinding.inflate(inflater, this, true)
    }

    /**
     * Setting retry callback
     */
    fun setRetryCallback(retryCallback: RetryCallback) {
        binding.retryCallback = retryCallback
    }

    /**
     * To show progress bar with given message
     */
    fun showLoading(message: String) {
        binding.isLoading = true
        binding.isError = false
        binding.message = message
    }

    fun showLoading(@StringRes message: Int) {
        showLoading(context.getString(message))
    }

    fun hideLoading() {
        binding.isLoading = false
        binding.isError = false
        binding.message = null
    }

    fun showError(message: String) {
        binding.isError = true
        binding.isLoading = false
        binding.message = message
    }

    interface RetryCallback {
        fun onRetryClicked()
    }
}