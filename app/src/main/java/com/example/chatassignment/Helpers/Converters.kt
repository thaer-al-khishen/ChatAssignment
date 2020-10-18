package com.example.chatassignment.Helpers

import androidx.room.TypeConverter
import java.util.*

//Helper class for room database to deal with dates
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
