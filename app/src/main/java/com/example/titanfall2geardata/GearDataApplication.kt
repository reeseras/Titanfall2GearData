package com.example.titanfall2geardata

import android.app.Application

class GearDataApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GearRepository.initialize(this)
    }
}