package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Arm(
    @PrimaryKey
    val armID: Int,
    val bezeichnung: String
)