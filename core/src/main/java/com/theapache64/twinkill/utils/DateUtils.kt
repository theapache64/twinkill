package com.theapache64.twinkill.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Everything you need to do with dates
 */
object DateUtils {

    /**
     * Sample  : Tuesday July 24, 2012
     */
    private val FORMAT_DAY_MONTH_DATE_YEAR by lazy {
        SimpleDateFormat(
            "EEEE MMMM dd, yyyy",
            Locale.US
        )
    }


    /**
     * Sample : 31-12-2019
     */
    private val FORMAT_DD_MM_YYYY by lazy { SimpleDateFormat("dd-MM-yyyy", Locale.US) }

    /**
     * Returns date in format `EEEE MMMM dd, yyyy`, ie, eg: Tuesday July 24, 2012
     * Returns null if the passed date is null
     */
    @JvmStatic
    fun getReadableFormat(date: Date?): String? {
        date?.let {
            return FORMAT_DAY_MONTH_DATE_YEAR.format(it)
        }
        return null
    }

    @JvmStatic
    fun toDDMMYYY(date: Date): String {
        return FORMAT_DD_MM_YYYY.format(date)
    }

}