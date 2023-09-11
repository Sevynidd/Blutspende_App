package agb.loehne.blutspende_app.datastore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoreSettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = StoreSettingsManager(application)

    /**
     * 0 = System Default
     * 1 = Light Mode
     * 2 = Dark Mode
     */
    val getThemeMode = dataStore.dataStoreMap

    fun saveToDataStore(themeMode: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveToDataStore(themeMode)
        }
    }
}