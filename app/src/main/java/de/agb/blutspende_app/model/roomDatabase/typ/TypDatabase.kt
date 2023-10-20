package de.agb.blutspende_app.model.roomDatabase.typ

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Typ::class], version = 1)
abstract class TypDatabase : RoomDatabase() {
    abstract fun typDao(): TypDao

}