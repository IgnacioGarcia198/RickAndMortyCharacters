import plugins.classloader.Projects

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
    implementation(project(Projects.FeatureCharacters.DATA))
    implementation(project(Projects.FeatureCharacters.DATA_PAGING))
    implementation(project(Projects.Common.KOTLIN_UTILS))
    implementation(project(Projects.Framework.REMOTE))

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}