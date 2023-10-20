package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BlutwerteDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addBlutwert(blutwert: Blutwerte)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArm(arm: Arm)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTyp(typ: Typ)


    @Delete
    suspend fun deleteBlutwert(blutwert: Blutwerte)

    @Delete
    suspend fun deleteArm(arm: Arm)

    @Delete
    suspend fun deleteTyp(typ: Typ)

    @Transaction
    @Query("SELECT * FROM Blutwerte WHERE fArmID = :armID AND fTypID = :typID ORDER BY blutwerteID ASC")
    fun readAllData(armID: Int, typID: Int): Flow<List<Blutwerte>>
}