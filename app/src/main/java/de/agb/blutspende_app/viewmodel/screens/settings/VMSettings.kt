package de.agb.blutspende_app.viewmodel.screens.settings

import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.R

class VMSettings : ViewModel() {
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