plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
}

android {
    namespace = "com.ignacio.rickandmorty.data"
}

dependencies {
    implementation(project(":feature-characters:domain"))

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common.jvm)
}
