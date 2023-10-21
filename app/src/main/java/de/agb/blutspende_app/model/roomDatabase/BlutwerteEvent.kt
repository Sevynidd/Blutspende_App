package de.agb.blutspende_app.model.roomDatabase

sealed interface BlutwerteEvent {
    data object SaveBlutwert : BlutwerteEvent
    data class DeleteBlutwert(val blutwert: Blutwerte) : BlutwerteEvent

    data class SetSystolisch(val sys: Int) : BlutwerteEvent
    data class SetDiastolisch(val dia: Int) : BlutwerteEvent
    data class SetPuls(val puls: Int) : BlutwerteEvent
    data class SetHaemoglobin(val haemoglobin: Float) : BlutwerteEvent
    data class FArmID(val armID: Int) : BlutwerteEvent
    data class FTypID(val typID: Int) : BlutwerteEvent

}