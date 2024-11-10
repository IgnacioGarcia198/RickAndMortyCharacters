package com.ignacio.rickandmorty.framework.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ignacio.rickandmorty.framework.local.db.converters.DbConverters
import com.ignacio.rickandmorty.framework.local.db.dao.RMCharacterDao
import com.ignacio.rickandmorty.framework.local.models.DbRMCharacter

@Database(entities = [DbRMCharacter::class], version = 1)
@TypeConverters(DbConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rmCharacterDao(): RMCharacterDao
}
