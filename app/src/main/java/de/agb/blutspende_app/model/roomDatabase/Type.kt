package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Type(
    @PrimaryKey
    val typID: Int,
    val blutspendeTyp: String
)