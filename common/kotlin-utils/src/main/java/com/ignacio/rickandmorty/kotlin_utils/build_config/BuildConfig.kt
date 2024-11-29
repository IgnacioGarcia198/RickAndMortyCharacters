package com.ignacio.rickandmorty.kotlin_utils.build_config

object BuildConfig {
    var DEBUG = false
        private set

    fun setDebugOnStartup(debug: Boolean = true) {
        DEBUG = debug
    }
}
