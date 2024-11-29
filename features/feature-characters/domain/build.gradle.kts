import plugins.classloader.Projects

plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
}

dependencies {
    implementation(project(Projects.Common.KOTLIN_UTILS))
    implementation(libs.com.google.dagger)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(testFixtures(project(Projects.Common.KOTLIN_UTILS)))
    testImplementation(libs.junit)
    testImplementation(libs.mockk.jvm)
    testImplementation(libs.kotlinx.coroutines.test)
}
