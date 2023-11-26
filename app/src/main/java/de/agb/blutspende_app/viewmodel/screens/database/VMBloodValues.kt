package de.agb.blutspende_app.viewmodel.screens.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.agb.blutspende_app.model.roomDatabase.BloodValues
import de.agb.blutspende_app.model.roomDatabase.BloodValuesDao
import de.agb.blutspende_app.model.roomDatabase.BloodValuesEvent
import de.agb.blutspende_app.model.roomDatabase.BloodValuesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VMBloodValues(
    private val bloodValuesDao: BloodValuesDao
) : ViewModel() {
    private val _state = MutableStateFlow(BloodValuesState())
    private val _bloodvalues =
        bloodValuesDao.getBloodValues(_state.value.fArmID, _state.value.fTypID)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _type = bloodValuesDao.getTypes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _arm = bloodValuesDao.getArms()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    val state = combine(_state, _bloodvalues, _type, _arm) { state, blood, type, arm ->
        state.copy(
            bloodValuesList = blood,
            typesList = type,
            armsList = arm
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BloodValuesState())

    fun onEvent(event: BloodValuesEvent) {
        when (event) {

            BloodValuesEvent.SaveBloodValues -> {
                val sys = _state.value.systolic
                val dia = _state.value.diastolic
                val puls = _state.value.pulse
                val haemoglobin = _state.value.haemoglobin
                val fArmID = _state.value.fArmID
                val fTypID = _state.value.fTypID
                val timestamp = _state.value.timestamp

                if ((sys == 0) or
                    (dia == 0) or
                    (puls == 0)
                ) {
                    return
                }

                val bloodValue = BloodValues(
                    systolisch = sys,
                    diastolisch = dia,
                    puls = puls,
                    haemoglobin = haemoglobin,
                    fArmID = fArmID,
                    fTypID = fTypID,
                    timestamp = timestamp
                )

                viewModelScope.launch {
                    bloodValuesDao.addBloodValue(bloodValue)
                }

                _state.update {
                    it.copy(
                        systolic = 0,
                        diastolic = 0,
                        pulse = 0,
                        haemoglobin = 0.0f,
                        fArmID = 0,
                        fTypID = 0,
                        timestamp = 0
                    )
                }
            }

            is BloodValuesEvent.DeleteBloodValues -> {
                viewModelScope.launch {
                    bloodValuesDao.deleteBloodValue(event.bloodValue)
                }
            }

            is BloodValuesEvent.SetSystolic -> {
                _state.update {
                    it.copy(
                        systolic = event.sys
                    )
                }
            }

            is BloodValuesEvent.SetDiastolic -> {
                _state.update {
                    it.copy(
                        diastolic = event.dia
                    )
                }
            }

            is BloodValuesEvent.SetPulse -> {
                _state.update {
                    it.copy(
                        pulse = event.pulse
                    )
                }
            }

            is BloodValuesEvent.SetHaemoglobin -> {
                _state.update {
                    it.copy(
                        haemoglobin = event.haemoglobin
                    )
                }
            }

            is BloodValuesEvent.FArmID -> {
                _state.update {
                    it.copy(
                        fArmID = event.armID
                    )
                }
            }

            is BloodValuesEvent.FTypID -> {
                _state.update {
                    it.copy(
                        fTypID = event.typID
                    )
                }
            }

            is BloodValuesEvent.SetTimestamp -> {
                _state.update {
                    it.copy(
                        timestamp = event.timestamp
                    )
                }
            }
        }
    }
}