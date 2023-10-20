package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [
    Index(value = arrayOf("armID", "bezeichnung"))
])
data class Arm(
    @PrimaryKey
    val armID: Int,
    val bezeichnung: String
)