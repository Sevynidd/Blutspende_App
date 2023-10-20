package de.agb.blutspende_app.model.roomDatabase.blutwerte

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Blutwerte(
    @PrimaryKey(autoGenerate = true)
    val blutwerteID: Int = 0,
    val systolisch: Int,
    val diastolisch: Int,
    val puls: Int,
    val haemoglobin: Float,
    val timestamp: Date,
    val fArmID: Int,
    val fTypID: Int

)