package com.example.titanfall2geardata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class GearDetailViewModel : ViewModel() {

    private val gearRepository = GearRepository.get()
    private val gearIdLiveData = MutableLiveData<UUID>()

    var gearLiveData: LiveData<Gear?> =
        Transformations.switchMap(gearIdLiveData) { gearId ->
            gearRepository.getAGear(gearId)
        }

    fun loadGear(gearId: UUID) {
        gearIdLiveData.value = gearId
    }

    fun saveGear(gear: Gear) {
        gearRepository.updateGear(gear)
    }
}