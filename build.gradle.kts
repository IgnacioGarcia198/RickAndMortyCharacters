// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    id(libs.plugins.android.module.plugin.get().pluginId) apply false
    id(libs.plugins.kotlin.module.plugin.get().pluginId) apply false
    id(libs.plugins.jetpack.compose.plugin.get().pluginId) apply false
    id(libs.plugins.deps.classes.loader.plugin.get().pluginId)
}
