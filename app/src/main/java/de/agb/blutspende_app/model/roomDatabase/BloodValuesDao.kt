package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodValuesDao {

    @Upsert
    suspend fun addBloodValue(bloodvalue: BloodValues)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArm(arm: Arm)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addType(type: Type)

    @Delete
    suspend fun deleteBloodValue(bloodValue: BloodValues)

    @Query("SELECT * FROM BloodValues WHERE fArmID = :armID AND fTypID = :typID ORDER BY timestamp ASC, blutwerteID ASC")
    fun getBloodValues(armID: Int, typID: Int): Flow<List<BloodValues>>

    @Query("SELECT * FROM Type ORDER BY typID ASC")
    fun getTypes(): Flow<List<Type>>

    @Query("SELECT * FROM Arm ORDER BY armID ASC")
    fun getArms(): Flow<List<Arm>>

    @Query("SELECT * FROM BloodValues WHERE fArmID = :armID AND fTypID = :typID AND timestamp BETWEEN :beginDate AND :endDate ORDER BY timestamp ASC, blutwerteID ASC")
    fun getBloodValuesFilteredDates(
        armID: Int,
        typID: Int,
        beginDate: Int,
        endDate: Int
    ): Flow<List<BloodValues>>

}