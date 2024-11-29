import plugins.classloader.Projects

plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
}

dependencies {
    implementation(project(Projects.Common.NETWORK))
    implementation(project(Projects.Features.NetworkMonitor.DOMAIN))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.com.google.dagger)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.jvm)
}