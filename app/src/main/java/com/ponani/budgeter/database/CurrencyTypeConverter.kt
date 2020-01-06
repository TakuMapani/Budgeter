package com.ponani.budgeter.database

import androidx.room.TypeConverter
import java.math.BigDecimal

class CurrencyTypeConverter {
    @TypeConverter
    fun fromLong(value: Long?): BigDecimal?{
        return if(value == null) null else BigDecimal(value).divide(BigDecimal(100))
    }

    @TypeConverter
    fun toLong (value : BigDecimal?): Long?{
        return if(value == null) null else value.multiply(BigDecimal(100)).longValueExact()
    }
}