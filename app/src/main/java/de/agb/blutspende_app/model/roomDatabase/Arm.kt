package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Arm(
    @PrimaryKey(autoGenerate = true)
    val armID: Int = 0,
    val bezeichnung: String
)