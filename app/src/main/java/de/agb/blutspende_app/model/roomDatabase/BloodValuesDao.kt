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

    @Query("SELECT * FROM BloodValues INNER JOIN Arm ON BloodValues.fArmID == Arm.armID INNER JOIN Type ON BloodValues.fTypID == Type.typID ORDER BY timestamp ASC, blutwerteID ASC")
    fun getBloodValues(): Flow<List<BloodValues>>

    @Query("SELECT * FROM Type ORDER BY typID ASC")
    fun getTypes(): Flow<List<Type>>

    @Query("SELECT * FROM Arm ORDER BY armID ASC")
    fun getArms(): Flow<List<Arm>>
}