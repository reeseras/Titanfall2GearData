package com.example.titanfall2geardata.GearDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.titanfall2geardata.Gear

@Database(entities = [Gear::class], version = 1)
@TypeConverters(GearTypeConverters::class)

abstract class GearDatabase : RoomDatabase() {

    abstract fun gearDao(): GearDao // generat DAO
}