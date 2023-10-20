package de.agb.blutspende_app.model.roomDatabase.typ

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TypDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTyp(typ: Typ)

    @Query("SELECT * FROM typ ORDER BY typID ASC")
    fun readAllTypes(): Flow<List<Typ>>
}