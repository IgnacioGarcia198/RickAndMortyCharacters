import plugins.classloader.Projects

plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
}

dependencies {
    implementation(project(Projects.Common.KOTLIN_UTILS))

    implementation(libs.androidx.paging.common.jvm)
}