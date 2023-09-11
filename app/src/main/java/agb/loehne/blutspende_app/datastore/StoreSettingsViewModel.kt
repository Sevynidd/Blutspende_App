package agb.loehne.blutspende_app.datastore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoreSettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val uiDataStore = StoreSettingsManager(application)

    val getUIMode = uiDataStore.uiMode

    fun saveToDataStore(isNightMode: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            uiDataStore.saveToDataStore(isNightMode)
        }
    }
}