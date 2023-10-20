package de.agb.blutspende_app.viewmodel.screens.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.agb.blutspende_app.model.roomDatabase.Arm
import de.agb.blutspende_app.model.roomDatabase.BlutwerteDao
import de.agb.blutspende_app.model.roomDatabase.ArmEvent
import de.agb.blutspende_app.model.roomDatabase.ArmState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VMArm(
    private val armDao: BlutwerteDao
) : ViewModel() {
    private val _state = MutableStateFlow(ArmState())

    fun onEvent(event: ArmEvent) {
        when (event) {
            ArmEvent.SaveArm -> {
                val bezeichnung = _state.value.bezeichnung

                if (bezeichnung.isBlank()) {
                    return
                }

                val arm = Arm(
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

            is ArmEvent.SetBezeichnung -> {
                _state.update {
                    it.copy(
                        bezeichnung = event.bezeichnung
                    )
                }
            }

            is ArmEvent.DeleteArm -> {
                viewModelScope.launch {
                    armDao.deleteArm(event.arm)
                }
            }
        }
    }
}