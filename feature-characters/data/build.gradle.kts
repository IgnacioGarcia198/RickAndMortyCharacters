plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.ignacio.rickandmorty.data"
}

dependencies {
    implementation(project(":feature-characters:domain"))

    //implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common.jvm)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}
