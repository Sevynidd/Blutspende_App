package de.agb.blutspende_app.model.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = arrayOf("blutwerteID", "sys", "dia", "puls")),
        Index(value = arrayOf("blutwerteID", "fArmID", "fTypID"))],
    foreignKeys = [
        ForeignKey(
            entity = Arm::class,
            parentColumns = arrayOf("armID"),
            childColumns = arrayOf("fArmID")
        ),
        ForeignKey(
            entity = Typ::class,
            parentColumns = arrayOf("typID"),
            childColumns = arrayOf("fTypID")
        )
    ]
)
data class Blutwerte(
    @PrimaryKey(autoGenerate = true)
    val blutwerteID: Int = 0,
    @ColumnInfo("sys")
    val systolisch: Int,
    @ColumnInfo("dia")
    val diastolisch: Int,
    val puls: Int,
    val haemoglobin: Float,
    //val timestamp: Long,
    val fArmID: Int,
    val fTypID: Int

)