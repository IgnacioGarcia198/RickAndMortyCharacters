import plugins.classloader.Projects

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    id(libs.plugins.jetpack.compose.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.ignacio.rickandmorty.auth.ui"
}

dependencies {
    implementation(project(Projects.Common.RESOURCES))
    implementation((project(Projects.Common.UI_COMMON)))
    implementation((project(Projects.Common.KOTLIN_UTILS)))
    implementation(project(Projects.FeatureAuth.PRESENTATION))
    implementation(project(Projects.FeatureNetworkMonitor.UI))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.viewmodel.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.io.coil.compose)
    implementation(libs.io.coil.network.ktor2)
    implementation(libs.io.ktor.client.cio)

    implementation(platform(libs.com.google.firebase.bom))
    implementation(libs.com.google.firebase.auth)
    implementation(libs.com.google.android.play.auth)
}