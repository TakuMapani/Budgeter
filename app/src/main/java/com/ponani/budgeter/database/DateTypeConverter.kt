package com.ponani.budgeter.database

import androidx.room.TypeConverter
import java.util.*

/**
 * The data types of both Long and Date can be null
 * They need to have ? ie Long? to prevent them from always being null
 */
class DateTypeConverter {
    @TypeConverter
    fun toDate(value: Long?) : Date?{
        return if(value == null) null else Date(value)
    }

    @TypeConverter
    fun toLong(value: Date?): Long?{
        return if (value == null) null else value.getTime()
    }
}