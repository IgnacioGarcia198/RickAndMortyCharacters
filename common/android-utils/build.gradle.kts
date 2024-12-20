plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.ignacio.rickandmorty.android_utils"

    @Suppress("UnstableApiUsage")
    testFixtures { enable = true } // only works for Java sources on Android modules :(
}

dependencies {
    implementation(libs.androidx.core.ktx)

    debugImplementation(libs.hilt.android)
    kspDebug(libs.hilt.android.compiler)

    testFixturesImplementation(libs.androidx.runner)
    testFixturesImplementation(libs.hilt.android.testing)
}