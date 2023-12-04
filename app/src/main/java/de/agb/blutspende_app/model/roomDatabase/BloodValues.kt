package de.agb.blutspende_app.model.roomDatabase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Arm::class,
            parentColumns = arrayOf("armID"),
            childColumns = arrayOf("fArmID")
        ),
        ForeignKey(
            entity = Type::class,
            parentColumns = arrayOf("typID"),
            childColumns = arrayOf("fTypID")
        )
    ]
)
data class BloodValues(
    @PrimaryKey(autoGenerate = true)
    val blutwerteID: Int = 0,
    val systolisch: Int,
    val diastolisch: Int,
    val haemoglobin: Float,
    val puls: Int,
    val timestamp: Long,
    val fArmID: Int,
    val fTypID: Int
)