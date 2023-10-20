package de.agb.blutspende_app.model.roomDatabase.arm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArmDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArm(arm: Arm)

    @Delete
    suspend fun deleteArm(arm: Arm)

    @Query("SELECT * FROM arm ORDER BY armID ASC")
    fun readAllArms(): Flow<List<Arm>>
}