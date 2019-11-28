package com.theapache64.twinkill.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
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
        visibility = View.VISIBLE
        binding.isLoading = true
        binding.isError = false
        binding.message = message
    }

    fun showLoading(@StringRes message: Int) {
        showLoading(context.getString(message))
    }

    fun hideLoading() {
        visibility = View.GONE
        binding.isLoading = false
        binding.isError = false
        binding.message = null
    }

    fun showError(message: String) {
        visibility = View.VISIBLE
        binding.isError = true
        binding.isLoading = false
        binding.message = message
    }

    fun setTextColor(@ColorRes color: Int) {
        binding.tvMessage.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setErrorRes(@DrawableRes drawable: Int) {
        binding.ivError.setImageResource(drawable)
    }

    interface RetryCallback {
        fun onRetryClicked()
    }
}