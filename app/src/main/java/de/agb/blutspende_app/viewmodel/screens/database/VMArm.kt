package de.agb.blutspende_app.viewmodel.screens.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.agb.blutspende_app.model.roomDatabase.Arm
import de.agb.blutspende_app.model.roomDatabase.BlutwerteDao
import de.agb.blutspende_app.model.roomDatabase.BlutwerteEvent
import de.agb.blutspende_app.model.roomDatabase.BlutwerteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VMArm(
    private val armDao: BlutwerteDao
) : ViewModel() {
    private val _state = MutableStateFlow(BlutwerteState())

    fun onEvent(event: BlutwerteEvent) {
        when (event) {
            BlutwerteEvent.SaveArm -> {
                val bezeichnung = _state.value.bezeichnung
                val armID = _state.value.armID

                if (bezeichnung.isBlank()) {
                    return
                }

                val arm = Arm(
                    armID = armID,
                    bezeichnung = bezeichnung
                )

                viewModelScope.launch {
                    armDao.addArm(arm)
                }

                _state.update {
                    it.copy(
                        bezeichnung = ""
                    )
                }
            }

            is BlutwerteEvent.SetBezeichnung -> {
                _state.update {
                    it.copy(
                        bezeichnung = event.bezeichnung
                    )
                }
            }

            is BlutwerteEvent.DeleteArm -> {
                viewModelScope.launch {
                    armDao.deleteArm(event.arm)
                }
            }
        }
    }
}