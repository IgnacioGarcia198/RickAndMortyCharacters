import plugins.classloader.Projects

plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.devtools.ksp)
}

dependencies {
    implementation(project(Projects.NETWORK))
    implementation(project(Projects.FeatureNetworkMonitor.DOMAIN))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.com.google.dagger)
}