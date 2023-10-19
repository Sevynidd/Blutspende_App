package de.agb.blutspende_app.model

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
        // SETTINGS
        private val THEME_MODE_KEY = intPreferencesKey("theme_mode")
        private val GENDER_KEY = booleanPreferencesKey("gender")

        // BLUT
        private val BLUTGRUPPE_KEY = intPreferencesKey("blutgruppe")
        private val RHESUS_KEY = booleanPreferencesKey("rhesus")
        private val RHESUS_KOMPLEX_KEY = stringPreferencesKey("rhesus_komplex")
        private val KELL_KEY = booleanPreferencesKey("kell")

        // BLUTSPENDE INFO URL
        private val BLUTSPENDE_INFO_URL_KEY = stringPreferencesKey("blutspende_info_url_key")
    }

    // SETTINGS

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
     * @param gender false (0) = Männlich; true (1) = Weiblich
     */
    suspend fun saveGenderToDataStore(gender: Boolean) {
        dataStore.edit { preferences ->
            preferences[GENDER_KEY] = gender
        }
    }

    val getGender: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[GENDER_KEY] ?: false
        }

    // BLUT

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


    // BLUTSPENDE INFO URL

    /**
     * @param url URL, welche gespeichert werden soll
     */
    suspend fun saveBlutspendeInfoURLToDataStore(url: String) {
        dataStore.edit { preferences ->
            preferences[BLUTSPENDE_INFO_URL_KEY] = url
        }
    }

    val getBlutspendeInfoURL: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[BLUTSPENDE_INFO_URL_KEY] ?: ""

        }
}