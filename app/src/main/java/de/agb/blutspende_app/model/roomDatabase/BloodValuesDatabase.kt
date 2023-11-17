package de.agb.blutspende_app.model.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        BloodValues::class,
        Arm::class,
        Type::class
    ], version = 1, exportSchema = true
)
abstract class BloodValuesDatabase : RoomDatabase() {
    abstract fun bloodValuesDao(): BloodValuesDao

    // Singleton for the database
    companion object {
        @Volatile
        private var INSTANCE: BloodValuesDatabase? = null

        fun getInstance(context: Context): BloodValuesDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BloodValuesDatabase::class.java,
                    "Blutspende_db"
                ).fallbackToDestructiveMigrationOnDowngrade().createFromAsset("database/Blutspende.db")
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}