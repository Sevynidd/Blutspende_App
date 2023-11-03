package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = arrayOf("typID", "blutspendeTyp"))
    ]
)
data class Type(
    @PrimaryKey
    val typID: Int,
    val blutspendeTyp: String
)