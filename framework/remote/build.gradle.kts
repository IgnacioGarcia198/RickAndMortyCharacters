import plugins.classloader.Projects

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ignacio.rickandmorty.framework.remote"
}

dependencies {
    implementation(project(Projects.FeatureCharacters.DATA_PAGING))
    implementation(project(Projects.FeatureCharacters.DATA))
    implementation(project(Projects.Common.KOTLIN_UTILS))
    implementation(project(Projects.NETWORK))

    implementation(libs.io.ktor.client.cio)
    implementation(libs.io.ktor.client.logging)
    implementation(libs.io.ktor.client.content.negotiation)
    implementation(libs.io.ktor.client.serialization.kotlinx.json)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.io.ktor.client.mock)
    testImplementation(libs.mockk.jvm)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(testFixtures(project(Projects.Common.KOTLIN_UTILS)))
}