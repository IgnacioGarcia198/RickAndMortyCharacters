plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.ignacio.rickandmorty.presentation"
}

dependencies {
    implementation(project(":feature-characters:domain"))

    implementation(libs.androidx.core.ktx)
    //implementation(libs.androidx.appcompat)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.paging.common.jvm)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.compose.runtime)

    testImplementation(libs.junit)
}