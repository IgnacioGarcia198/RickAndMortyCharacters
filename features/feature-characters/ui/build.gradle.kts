import plugins.classloader.Projects

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    id(libs.plugins.jetpack.compose.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.ignacio.rickandmorty.ui"
}

dependencies {
    implementation(project(Projects.Common.RESOURCES))
    implementation((project(Projects.Common.UI_COMMON)))
    implementation((project(Projects.Common.KOTLIN_UTILS)))
    implementation(project(Projects.FeatureCharacters.PRESENTATION))
    implementation(project(Projects.FeatureCharacters.DOMAIN))
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
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.paging.compose) {
        exclude(group = "androidx.paging", module = "paging-common-android")
    }
    implementation(libs.io.coil.compose)
    implementation(libs.io.coil.network.ktor2)
    implementation(libs.io.ktor.client.cio)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)
    // need to import these 2 projects for Android testing so Hilt dependencies work, not a big deal.
    androidTestImplementation(project(Projects.Framework.DI_CONNECTOR))
    androidTestImplementation(project(Projects.Framework.LOCAL))
    androidTestImplementation(project(Projects.Common.ANDROID_UTILS))
    androidTestImplementation(testFixtures(project(Projects.Common.ANDROID_UTILS)))

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}