package com.puntogris.recap.utils

import androidx.room.TypeConverter
import java.util.*

object Converters {

    @JvmStatic
    @TypeConverter
    fun toTimestamp(dateLong: Long?): Date? = dateLong?.let { Date(dateLong) }

    @JvmStatic
    @TypeConverter
    fun fromTimestamp(date: Date?): Long? = date?.time
}