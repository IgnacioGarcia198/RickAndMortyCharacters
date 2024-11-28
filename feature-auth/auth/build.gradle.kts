import plugins.classloader.Projects

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.services)
}

android {
    namespace = "com.ignacio.rickandmorty.auth.auth"
}

dependencies {
    implementation(project(Projects.FeatureAuth.DOMAIN))

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(platform(libs.com.google.firebase.bom))
    implementation(libs.com.google.firebase.auth)
    implementation(libs.com.google.android.play.auth)
}