package com.theapache64.twinkill.utils.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * To show short toast message
 */
fun Fragment.toast(message: String) {
    activity?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}

/**
 * To show short toast message with xml string
 */
fun Fragment.toast(@StringRes message: Int) {
    activity?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}


/**
 * To show long toast message
 */
fun Fragment.longToast(message: String) {
    activity?.let {
        Toast.makeText(it, message, Toast.LENGTH_LONG).show()
    }
}

/**
 * To show long toast message with xml string
 */
fun Fragment.longToast(@StringRes message: Int) {
    activity?.let {
        Toast.makeText(it, message, Toast.LENGTH_LONG).show()
    }
}