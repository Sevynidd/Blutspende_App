package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodValuesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addBloodValue(bloodvalue: BloodValues)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArm(arm: Arm)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addType(type: Type)

    @Update
    suspend fun editBloodValue(bloodValue: BloodValues)

    @Delete
    suspend fun deleteBloodValue(bloodValue: BloodValues)

    @Transaction
    @Query("SELECT * FROM BloodValues WHERE fArmID = :armID AND fTypID = :typID ORDER BY blutwerteID ASC")
    fun allData(armID: Int, typID: Int): Flow<List<BloodValues>>

    @Transaction
    @Query("SELECT * FROM Type")
    fun readType(): Flow<List<Type>>

    @Transaction
    @Query("SELECT * FROM Arm")
    fun readArm(): Flow<List<Arm>>
}