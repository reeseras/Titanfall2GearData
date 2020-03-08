package com.example.titanfall2geardata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.titanfall2geardata.GearDatabase.GearDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "gear-database"

class GearRepository private constructor(context: Context) {

    private val database : GearDatabase = Room.databaseBuilder(
        context.applicationContext,
        GearDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val gearDao = database.gearDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllGear(): LiveData<List<Gear>> = gearDao.getAllGear()

    fun getAGear(id: UUID): LiveData<Gear?> = gearDao.getAGear(id)

    fun updateGear(gear: Gear) {
        executor.execute {
            gearDao.updateGear(gear)
        }
    }

    fun addGear(gear: Gear) {
        executor.execute {
            gearDao.addGear(gear)
        }
    }

    companion object {
        private var INSTANCE: GearRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = GearRepository(context)
            }
        }

        fun get(): GearRepository {
            return INSTANCE ?:
                throw IllegalStateException("GearRepository needs to be initialized - R")
        }
    }
}