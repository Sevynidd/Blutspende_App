package de.agb.blutspende_app.viewmodel.screens.settings

import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.R

class VMSettings : ViewModel() {

    // THEME
    private val _imageIdsTheme =
        listOf(R.drawable.ui_system, R.drawable.ui_hell, R.drawable.ui_dunkel)
    val getImageIdsTheme: List<Int>
        get() = _imageIdsTheme

    // GENDER
    private val _imageIdsGender =
        listOf(R.drawable.gender_male, R.drawable.gender_female)

    val getImageIdsGender: List<Int>
        get() = _imageIdsGender

}