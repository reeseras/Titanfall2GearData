package com.example.titanfall2geardata

import android.app.Application
import com.example.titanfall2geardata.GearRepository

class GearDataApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GearRepository.initialize(this)
    }
}