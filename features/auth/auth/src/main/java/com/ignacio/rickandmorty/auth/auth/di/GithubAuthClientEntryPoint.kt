package com.ignacio.rickandmorty.auth.auth.di

import com.ignacio.rickandmorty.auth.auth.google.GithubAuthUiClient
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface GithubAuthClientEntryPoint {
    fun githubUiClient(): GithubAuthUiClient
}