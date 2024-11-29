plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("java-test-fixtures")
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.com.google.dagger)

    testFixturesImplementation(libs.kotlinx.coroutines.test)
    testFixturesImplementation(libs.junit)
}
