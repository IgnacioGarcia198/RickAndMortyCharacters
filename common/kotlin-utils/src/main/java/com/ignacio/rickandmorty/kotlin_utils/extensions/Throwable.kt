package com.ignacio.rickandmorty.kotlin_utils.extensions

import com.ignacio.rickandmorty.kotlin_utils.build_config.BuildConfig

fun Throwable.getDebugOrProductionText(): String =
    if (BuildConfig.DEBUG) stackTraceToString() else message.orEmpty()