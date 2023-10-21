package de.agb.blutspende_app.viewmodel.screens.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.agb.blutspende_app.model.roomDatabase.Blutwerte
import de.agb.blutspende_app.model.roomDatabase.BlutwerteDao
import de.agb.blutspende_app.model.roomDatabase.BlutwerteEvent
import de.agb.blutspende_app.model.roomDatabase.BlutwerteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VMBlutwerte(
    private val blutwerteDao: BlutwerteDao
) : ViewModel() {
    private val _state = MutableStateFlow(BlutwerteState())

    fun onEvent(event: BlutwerteEvent) {
        when (event) {

            is BlutwerteEvent.DeleteBlutwert -> {
                viewModelScope.launch {
                    blutwerteDao.deleteBlutwert(event.blutwert)
                }
            }

            is BlutwerteEvent.FArmID -> {
                _state.update {
                    it.copy(
                        fArmID = event.armID
                    )
                }
            }

            is BlutwerteEvent.FTypID -> {
                _state.update {
                    it.copy(
                        fTypID = event.typID
                    )
                }
            }

            BlutwerteEvent.SaveBlutwert -> {
                val sys = _state.value.systolisch
                val dia = _state.value.diastolisch
                val puls = _state.value.puls
                val haemoglobin = _state.value.haemoglobin
                val fArmID = _state.value.fArmID
                val fTypID = _state.value.fTypID

                if ((sys == 0) or
                    (dia == 0) or
                    (puls == 0) or
                    (haemoglobin == 0.0f)
                ) {
                    return
                }

                val blutwert = Blutwerte(
                    systolisch = sys,
                    diastolisch = dia,
                    puls = puls,
                    haemoglobin = haemoglobin,
                    fArmID = fArmID,
                    fTypID = fTypID
                )

                viewModelScope.launch {
                    blutwerteDao.addBlutwert(blutwert)
                }

                _state.update {
                    it.copy(
                        systolisch = 0,
                        diastolisch = 0,
                        puls = 0,
                        haemoglobin = 0.0f,
                        fArmID = 0,
                        fTypID = 0
                    )
                }
            }

            is BlutwerteEvent.SetDiastolisch -> {
                _state.update {
                    it.copy(
                        diastolisch = event.dia
                    )
                }
            }

            is BlutwerteEvent.SetHaemoglobin -> {
                _state.update {
                    it.copy(
                        haemoglobin = event.haemoglobin
                    )
                }
            }

            is BlutwerteEvent.SetPuls -> {
                _state.update {
                    it.copy(
                        puls = event.puls
                    )
                }
            }

            is BlutwerteEvent.SetSystolisch -> {
                _state.update {
                    it.copy(
                        systolisch = event.sys
                    )
                }
            }
        }
    }
}