package agb.loehne.blutspende_app.model

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class DatastoreManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private val THEME_MODE_KEY = intPreferencesKey("theme_mode")
        private val BLUTGRUPPE_KEY = intPreferencesKey("blutgruppe")
        private val RHESUS_KEY = booleanPreferencesKey("rhesus")
        private val RHESUS_KOMPLEX_KEY = stringPreferencesKey("rhesus_komplex")
        private val KELL_KEY = booleanPreferencesKey("kell")
    }

    /**
     * @param themeMode 0 = System Default; 1 = Light Mode; 2 = Dark Mode
     */
    suspend fun saveThemeToDataStore(themeMode: Int) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode
        }
    }

    val getTheme: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[THEME_MODE_KEY] ?: 0
        }

    /**
     * @param blutgruppe 0 = 0; 1 = A; 2 = B; 3 = AB
     */
    suspend fun saveBlutgruppeToDataStore(blutgruppe: Int) {
        dataStore.edit { preferences ->
            preferences[BLUTGRUPPE_KEY] = blutgruppe
        }
    }

    val getBlutgruppe: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[BLUTGRUPPE_KEY] ?: 0
        }

    /**
     * @param rhesus true = +; false = -
     */
    suspend fun saveRhesusToDataStore(rhesus: Boolean) {
        dataStore.edit { preferences ->
            preferences[RHESUS_KEY] = rhesus
        }
    }

    val getRhesus: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[RHESUS_KEY] ?: true
        }

    /**
     * @param rhesuskomplex Bsp.: CcD.ee
     */
    suspend fun saveRhesuskomplexToDataStore(rhesuskomplex: String) {
        dataStore.edit { preferences ->
            preferences[RHESUS_KOMPLEX_KEY] = rhesuskomplex
        }
    }

    val getRhesuskomplex: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[RHESUS_KOMPLEX_KEY] ?: ""
        }

    /**
     * @param kell true = +; false = -
     */
    suspend fun saveKellToDataStore(kell: Boolean) {
        dataStore.edit { preferences ->
            preferences[KELL_KEY] = kell
        }
    }

    val getKell: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[KELL_KEY] ?: false
        }


}