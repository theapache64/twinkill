package com.theapache64.twinkill.utils.extensions

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * To show short and simple snackbar with just a message
 */
fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

/**
 *To show Snackbar
 */
fun View.snackBar(@StringRes msgId: Int) {
    snackBar(context.getString(msgId))
}

/**
 * To show long snack bar
 * */
fun View.snackBarLong(@StringRes msgId: Int) {
    snackBarLong(context.getString(msgId))
}

/**
 * To show long snack bar
 */
fun View.snackBarLong(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}


