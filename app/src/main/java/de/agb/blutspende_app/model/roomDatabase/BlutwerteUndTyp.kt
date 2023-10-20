package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Embedded
import androidx.room.Relation

data class BlutwerteUndTyp(
    @Embedded val blutwerte: Blutwerte,
    @Relation(
        parentColumn = "fTypID",
        entityColumn = "typID"
    )
    val typ: Typ
)