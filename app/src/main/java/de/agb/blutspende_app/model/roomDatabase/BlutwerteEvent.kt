package de.agb.blutspende_app.model.roomDatabase

sealed interface BlutwerteEvent {
    data object SaveArm : BlutwerteEvent
    data class SetBezeichnung(val bezeichnung: String) : BlutwerteEvent

    data class DeleteArm(val arm: Arm) : BlutwerteEvent

}