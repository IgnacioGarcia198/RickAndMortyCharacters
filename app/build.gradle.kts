import plugins.classloader.Projects
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    id(libs.plugins.jetpack.compose.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.services)
}

val isRelease: Boolean = gradle.startParameter.taskNames.any { it.contains("Release") }
val keystoreProperties = loadKeystoreProperties()

fun loadKeystoreProperties(): Properties {
    return if (isRelease) {
        val keystorePropertiesFile = rootProject.file("key.properties")
        if (keystorePropertiesFile.exists()) {
            Properties().apply {
                FileInputStream(keystorePropertiesFile).use { load(it) }
            }
        } else {
            throw GradleException("CANNOT SIGN RELEASE: key.properties FILE MISSING!")
        }
    } else Properties()
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

    signingConfigs {
        create("release") {
            if (isRelease) {
                keyAlias = keystoreProperties["rickAndMortyKeyAlias"] as String
                keyPassword = keystoreProperties["rickAndMortyKeyPassword"] as String
                storeFile = file(keystoreProperties["rickAndMortyStoreFile"] as String)
                storePassword = keystoreProperties["rickAndMortyStorePassword"] as String
            }
        }
    }

    buildTypes {
        getByName("release") {
            if (isRelease) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}

dependencies {
    implementation(project(Projects.Features.Characters.UI))
    implementation(project(Projects.Features.Auth.UI))
    implementation(project(Projects.Features.MainNavigation.UI))
    implementation((project(Projects.Common.UI_COMMON)))
    implementation((project(Projects.Common.KOTLIN_UTILS)))
    implementation(project(Projects.Framework.LOCAL))
    implementation(project(Projects.Framework.DI_CONNECTOR)) // connect DI

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.io.coil.android)

    implementation(platform(libs.com.google.firebase.bom))
    implementation(libs.com.google.firebase.common)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
