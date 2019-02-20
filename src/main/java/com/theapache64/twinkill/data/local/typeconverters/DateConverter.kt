package com.theapache64.twinkill.data.local.typeconverters

import androidx.room.TypeConverter
import java.util.*

/**
 * DateConverter for Room
 */
class DateConverter {

    /**
     * Converts Date object to time in millis
     */
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    /**
     * Converts time in millis to Date object
     */
    @TypeConverter
    fun longToDate(long: Long?): Date? {
        return long?.let { Date(it) }
    }
}