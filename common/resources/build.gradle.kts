plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
}

android {
    namespace = "com.ignacio.rickandmorty.resources"
}
