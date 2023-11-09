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
    fun getBloodValues(armID: Int, typID: Int): Flow<List<BloodValues>>

    @Transaction
    @Query("SELECT * FROM Type ORDER BY typID ASC")
    fun getTypes(): Flow<List<Type>>

    @Transaction
    @Query("SELECT * FROM Arm ORDER BY armID ASC")
    fun getArms(): Flow<List<Arm>>
}