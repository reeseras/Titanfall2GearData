package com.example.titanfall2geardata.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.titanfall2geardata.Gear
import java.util.*

@Dao
interface GearDao {
    @Query("SELECT * FROM gear")
    fun getAllGear(): LiveData<List<Gear>>

    @Query("SELECT * FROM gear WHERE id=(:id)")
    fun getAGear(id: UUID): LiveData<Gear?>

    @Update
    fun updateGear(gear: Gear)

    @Insert
    fun addGear(gear: Gear)
}