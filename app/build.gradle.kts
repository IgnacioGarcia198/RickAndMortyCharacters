import plugins.classloader.Projects

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.ignacio.rickandmorty"

    defaultConfig {
        applicationId = "com.ignacio.rickandmorty"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures.buildConfig = true
}

dependencies {
    implementation(project(Projects.FeatureCharacters.UI))
    implementation((project(Projects.Common.UI_COMMON)))
    implementation((project(Projects.Common.KOTLIN_UTILS)))
    implementation(project(Projects.Framework.LOCAL))
    implementation(project(Projects.Framework.DI_CONNECTOR)) // connect DI

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.io.coil.android)
}