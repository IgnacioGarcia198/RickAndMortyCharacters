package com.ignacio.rickandmorty.auth.auth.di

import com.ignacio.rickandmorty.auth.auth.google.GoogleAuthUiClient
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GoogleAuthClientEntryPoint {
    fun googleAuthUiClient(): GoogleAuthUiClient
}