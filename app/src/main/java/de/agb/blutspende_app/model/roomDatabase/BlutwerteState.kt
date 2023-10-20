package de.agb.blutspende_app.model.roomDatabase

data class BlutwerteState(
    val arm: List<Arm> = emptyList(),
    val bezeichnung: String = "",
    val armID: Int = 0
)
