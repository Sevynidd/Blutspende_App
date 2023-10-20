package de.agb.blutspende_app.model.roomDatabase

data class ArmState(
    val arm: List<Arm> = emptyList(),
    val bezeichnung: String = ""
)
