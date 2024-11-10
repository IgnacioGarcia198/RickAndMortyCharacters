package com.ignacio.rickandmorty.framework.local.db.converters

import androidx.room.TypeConverter

class DbConverters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split(",")
    }
}