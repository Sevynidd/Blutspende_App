package agb.loehne.blutspende_app.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")
class StoreSettingsManager(context: Context) {

    private val dataStore = context.dataStore

    /**
     * @param themeMode 0 = System Default; 1 = Light Mode; 2 = Dark Mode
     */
    suspend fun saveToDataStore(themeMode: Int) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode
        }
    }

    val dataStoreMap: Flow<Any> = dataStore.data
        .map { preferences ->
            val themeMode = preferences[THEME_MODE_KEY] ?: 0
            themeMode
        }

    companion object {
        private val THEME_MODE_KEY = intPreferencesKey("theme_mode")
    }
}