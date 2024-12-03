import plugins.classloader.Projects

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.ignacio.rickandmorty.auth.presentation"
}

dependencies {
    implementation(project(Projects.Common.KOTLIN_UTILS))
    implementation(project(Projects.Features.Auth.DOMAIN))
    implementation(project(Projects.Features.Auth.AUTH))

    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.paging.common.jvm)
    implementation(libs.androidx.lifecycle.viewmodel)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.jvm)
    testImplementation(testFixtures(project(Projects.Common.KOTLIN_UTILS)))
    testImplementation(libs.kotlinx.coroutines.test)
}
