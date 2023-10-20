package de.agb.blutspende_app.model.roomDatabase.typ

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Typ (
    @PrimaryKey(autoGenerate = true)
    val typID: Int = 0,
    val blutspendeTyp: String
)