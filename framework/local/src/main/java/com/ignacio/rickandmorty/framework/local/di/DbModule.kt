package com.ignacio.rickandmorty.framework.local.di

import android.content.Context
import androidx.room.Room
import com.ignacio.rickandmorty.framework.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "app-database.db"
    ).build()
}
