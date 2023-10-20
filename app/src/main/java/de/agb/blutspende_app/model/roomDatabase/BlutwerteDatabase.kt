package de.agb.blutspende_app.model.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Blutwerte::class,
        Arm::class,
        Typ::class
    ], version = 2, exportSchema = false
)
abstract class BlutwerteDatabase : RoomDatabase() {
    abstract fun blutwerteDao(): BlutwerteDao

    companion object {
        @Volatile
        private var INSTANCE: BlutwerteDatabase? = null

        fun getInstance(context: Context): BlutwerteDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BlutwerteDatabase::class.java,
                    "Blutspende_db"
                ).fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}