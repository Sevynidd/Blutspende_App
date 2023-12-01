package de.agb.blutspende_app.model.roomDatabase

sealed interface BloodValuesEvent {
    data object SaveBloodValues : BloodValuesEvent
    data class DeleteBloodValues(val bloodValue: BloodValues) : BloodValuesEvent

    data class SetSystolic(val sys: Int) : BloodValuesEvent
    data class SetDiastolic(val dia: Int) : BloodValuesEvent
    data class SetPulse(val pulse: Int) : BloodValuesEvent
    data class SetHaemoglobin(val haemoglobin: Float) : BloodValuesEvent
    data class SetTimestamp(val timestamp: Long) : BloodValuesEvent
    data class SetFArmID(val armID: Int) : BloodValuesEvent
    data class SetFTypID(val typID: Int) : BloodValuesEvent

}