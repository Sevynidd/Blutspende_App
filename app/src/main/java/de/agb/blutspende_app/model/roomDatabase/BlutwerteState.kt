package de.agb.blutspende_app.model.roomDatabase

data class BlutwerteState(
    val blutwerte: List<Blutwerte> = emptyList(),
    val systolisch: Int = 0,
    val diastolisch: Int = 0,
    val puls: Int = 0,
    val haemoglobin: Float = 0.0f,
    val fArmID: Int = 0,
    val fTypID: Int = 0
)
