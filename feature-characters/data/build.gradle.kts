plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.devtools.ksp)
}

dependencies {
    implementation(project(":feature-characters:domain"))
    implementation(project(":kotlin-utils"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.com.google.dagger)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.jvm)
    testImplementation(libs.kotlinx.coroutines.test)
}
