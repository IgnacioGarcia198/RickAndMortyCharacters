plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.ignacio.rickandmorty.di_connector"
}

dependencies {
    implementation(project(":feature-characters:data"))
    implementation(project(":kotlin-utils"))
    implementation(project(":framework:remote"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}