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

        // BLOOD
        private val BLOODVALUE_KEY = intPreferencesKey("bloodvalue")
        private val RHESUS_KEY = booleanPreferencesKey("rhesus")
        private val RHESUSCOMPLEX_KEY = stringPreferencesKey("rhesus_complex")
        private val KELL_KEY = booleanPreferencesKey("kell")

        // BLOOD DONATION INFO URL
        private val BLOOD_DONATION_INFO_URL_KEY = stringPreferencesKey("blood_donation_info_url")
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
     * @param gender false (0) = Male; true (1) = Female
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
     * @param bloodGroup 0 = 0; 1 = A; 2 = B; 3 = AB
     */
    suspend fun saveBloodGroupToDataStore(bloodGroup: Int) {
        dataStore.edit { preferences ->
            preferences[BLOODVALUE_KEY] = bloodGroup
        }
    }

    val getBloodgroup: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[BLOODVALUE_KEY] ?: 0
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
     * @param rhesuscomplex ex.: CcD.ee
     */
    suspend fun saveRhesuscomplexToDataStore(rhesuscomplex: String) {
        dataStore.edit { preferences ->
            preferences[RHESUSCOMPLEX_KEY] = rhesuscomplex
        }
    }

    val getRhesuscomplex: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[RHESUSCOMPLEX_KEY] ?: ""
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


    // BLOOD DONATION INFO URL

    /**
     * @param url URL, which should be saved
     */
    suspend fun saveBloodDonationInfoURLToDataStore(url: String) {
        dataStore.edit { preferences ->
            preferences[BLOOD_DONATION_INFO_URL_KEY] = url
        }
    }

    val getBloodDonationInfoURL: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[BLOOD_DONATION_INFO_URL_KEY] ?: ""

        }
}