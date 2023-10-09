package de.agb.blutspende_app.viewmodel.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.ScreenDefinition

class SettingsViewModel : ViewModel() {
    // DARSTELLUNG

    private val _imageIdsDarstellung =
        listOf(R.drawable.ui_system, R.drawable.ui_hell, R.drawable.ui_dunkel)
    val getImageIdsDarstellung: List<Int>
        get() = _imageIdsDarstellung

    // GENDER

    private val _imageIdsGender =
        listOf(R.drawable.gender_male, R.drawable.gender_female)

    val getImageIdsGender: List<Int>
        get() = _imageIdsGender

}