import plugins.classloader.Projects

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    id(libs.plugins.jetpack.compose.plugin.get().pluginId)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ignacio.rickandmorty.main_navigation.ui"
}

dependencies {
    implementation(project(Projects.FeatureNetworkMonitor.UI))
    implementation(project(Projects.FeatureCharacters.UI))
    implementation(project(Projects.FeatureAuth.UI))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}
