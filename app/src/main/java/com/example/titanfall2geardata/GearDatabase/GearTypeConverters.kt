package com.example.titanfall2geardata.GearDatabase

import androidx.room.TypeConverter
import java.util.*

class GearTypeConverters {

    // date converter here if needed.

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}