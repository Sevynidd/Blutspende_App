package de.agb.blutspende_app.model.roomDatabase.arm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Arm::class], version = 1)
abstract class ArmDatabase : RoomDatabase() {
    abstract fun armDao(): ArmDao
}