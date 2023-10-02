package de.agb.blutspende_app.viewmodel

import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    fun topAppBarTitle(currentRoute: String?): String {
        return if (currentRoute == "SettingsBlutgruppe") {
            "Settings"
        } else {
            currentRoute ?: ""
        }
    }

}