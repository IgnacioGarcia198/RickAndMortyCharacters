plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.devtools.ksp)
}

dependencies {
    implementation(project(":feature-characters:data"))
    implementation(project(":feature-characters:domain"))
    implementation(project(":kotlin-utils"))
    implementation(project(":paging"))

    implementation(libs.androidx.paging.common.jvm)
    implementation(libs.com.google.dagger)
    ksp(libs.com.google.dagger.compiler)
}