package com.example.titanfall2geardata

import androidx.lifecycle.ViewModel

class GearListViewModel : ViewModel() {
    private val gearRepository = GearRepository.get()
    val gearListLiveData = gearRepository.getAllGear()

    fun addGear(gear: Gear) {
        gearRepository.addGear(gear)
    }
}