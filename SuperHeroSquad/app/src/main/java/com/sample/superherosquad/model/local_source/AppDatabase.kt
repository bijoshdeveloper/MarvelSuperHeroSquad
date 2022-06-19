package com.sample.superherosquad.model.local_source

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Room database implementation class.
 **/
@Database(
    entities = [CharacterEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}