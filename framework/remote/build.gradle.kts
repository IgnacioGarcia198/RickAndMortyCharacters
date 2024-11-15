import plugins.classloader.Projects

plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.com.google.devtools.ksp)
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
}

dependencies {
    implementation(project(Projects.FeatureCharacters.DATA_PAGING))
    implementation(project(Projects.FeatureCharacters.DATA))
    implementation(project(Projects.Common.KOTLIN_UTILS))

    implementation(libs.io.ktor.client.cio)
    implementation(libs.io.ktor.client.logging)
    implementation(libs.io.ktor.client.content.negotiation)
    implementation(libs.io.ktor.client.serialization.kotlinx.json)
    implementation(libs.com.google.dagger)
    ksp(libs.com.google.dagger.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.io.ktor.client.mock)
}