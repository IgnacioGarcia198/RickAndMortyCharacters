plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.ignacio.rickandmorty.network"
}

dependencies {
    implementation(project(":feature-characters:data"))

    implementation(libs.io.ktor.client.cio)
    implementation(libs.io.ktor.client.logging)
    implementation(libs.io.ktor.client.content.negotiation)
    implementation(libs.io.ktor.client.serialization.kotlinx.json)
    implementation(libs.com.google.dagger)
    ksp(libs.com.google.dagger.compiler)
}