package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Embedded
import androidx.room.Relation

data class BlutwerteUndArm (
    @Embedded val blutwerte: Blutwerte,
    @Relation(
        parentColumn = "fArmID",
        entityColumn = "armID"
    )
    val arm: Arm
)