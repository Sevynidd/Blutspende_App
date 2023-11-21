package de.agb.blutspende_app.model.roomDatabase

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDate (date: Date): Long {
        return date.time
    }
    @TypeConverter
    fun toDate (millis: Long): Date {
        return Date(millis)
    }
}