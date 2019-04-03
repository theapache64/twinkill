package com.theapache64.twinkill.utils.extensions

import android.util.Log

fun Any.debug(message: String) {
    Log.d(getTag(this), message)
}

fun Any.info(message: String) {
    Log.i(getTag(this), message)
}

fun Any.verbose(message: String) {
    Log.v(getTag(this), message)
}

fun Any.mistake(message: String) {
    Log.e(getTag(this), message)
}

fun Any.warning(message: String) {
    Log.w(getTag(this), message)
}

fun Any.wtf(message: String) {
    Log.wtf(getTag(this), message)
}


/**
 * Returns log tag name from Any object
 */
private fun getTag(any: Any): String? {

    var tag = any.javaClass.simpleName

    if (tag.isEmpty()) {
        tag = getSimpleNameFromName(any.javaClass.name)
    }

    require(tag.isNotEmpty()) { "Unable to get log tag for $any" }

    return tag
}

/**
 * Converts lengthy class name to shortened string
 */
private fun getSimpleNameFromName(name: String): String {
    val lastDotPos = name.lastIndexOf('.')
    val x = name.substring(lastDotPos)
    val y = x.split("$")
    return y[0].substring(1)
}
