package de.agb.blutspende_app.model.roomDatabase

import java.util.Date

data class BloodValuesState(
    val bloodValuesList: List<BloodValues> = emptyList(),
    val systolic: Int = 0,
    val diastolic: Int = 0,
    val pulse: Int = 0,
    val haemoglobin: Float = 0.0f,
    val fArmID: Int = 0,
    val armsList: List<Arm> = emptyList(),
    val fTypID: Int = 0,
    val typesList: List<Type> = emptyList(),
    val timestamp: Date = Date(0)
)
