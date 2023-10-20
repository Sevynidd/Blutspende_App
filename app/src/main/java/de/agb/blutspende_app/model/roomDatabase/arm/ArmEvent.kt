package de.agb.blutspende_app.model.roomDatabase.arm

sealed interface ArmEvent {
    data object SaveArm : ArmEvent
    data class SetBezeichnung(val bezeichnung: String) : ArmEvent

    data class DeleteArm(val arm: Arm) : ArmEvent

}