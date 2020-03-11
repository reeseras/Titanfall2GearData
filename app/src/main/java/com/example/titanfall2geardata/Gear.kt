package com.example.titanfall2geardata

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Gear(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var name: String = "",
                var type: String = "",
                var use: Boolean = true)