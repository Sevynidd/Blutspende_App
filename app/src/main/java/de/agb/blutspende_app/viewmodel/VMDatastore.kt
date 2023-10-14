package de.agb.blutspende_app.viewmodel

import de.agb.blutspende_app.model.DatastoreManager
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMDatastore(application: Application) : AndroidViewModel(application) {

    private val dataStore = DatastoreManager(application)

    /**
     * 0 = System Default; 1 = Light Mode; 2 = Dark Mode
     */
    val getThemeMode = dataStore.getTheme

    /**
     * 0 = System Default; 1 = Light Mode; 2 = Dark Mode
     */
    fun saveThemeToDataStore(themeMode: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveThemeToDataStore(themeMode)
        }
    }

    /**
     * false (0) = Männlich; true (1) = Weiblich
     */
    val getGender = dataStore.getGender

    /**
     * false (0) = Männlich; true (1) = Weiblich
     */
    fun saveGenderToDataStore(gender: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveGenderToDataStore(gender)
        }
    }

    /**
     * 0 = 0; 1 = A; 2 = B; 3 = AB
     */
    val getBlutgruppe = dataStore.getBlutgruppe

    /**
     * 0 = 0; 1 = A; 2 = B; 3 = AB
     */
    fun saveBlutgruppeToDataStore(blutgruppe: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveBlutgruppeToDataStore(blutgruppe)
        }
    }

    /**
     * true = +; false = -
     */
    val getRhesus = dataStore.getRhesus

    /**
     * true = +; false = -
     */
    fun saveRhesusToDataStore(rhesus: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveRhesusToDataStore(rhesus)
        }
    }

    /**
     * Bsp.: CcD.ee
     */
    val getRhesuskomplex = dataStore.getRhesuskomplex

    /**
     * true = +; false = -
     */
    fun saveRhesuskomplexToDataStore(rhesuskomplex: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveRhesuskomplexToDataStore(rhesuskomplex)
        }
    }

    /**
     * true = +; false = -
     */
    val getKell = dataStore.getKell

    /**
     * true = +; false = -
     */
    fun saveKellToDataStore(kell: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveKellToDataStore(kell)
        }
    }
}