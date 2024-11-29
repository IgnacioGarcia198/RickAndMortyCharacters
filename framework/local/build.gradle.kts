import plugins.classloader.Projects

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id(libs.plugins.android.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.androidx.room)
}

android {
    namespace = "com.ignacio.rickandmorty.framework.local"

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(project(Projects.Features.Characters.DATA))
    implementation(project(Projects.Features.Characters.DATA_PAGING))
    implementation(project(Projects.Common.KOTLIN_UTILS))

    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging) {
        exclude(group = "androidx.paging", module = "paging-common-android")
        exclude(group = "androidx.paging", module = "paging-common")
    }
    implementation(libs.androidx.paging.common.jvm)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.jvm)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(testFixtures(project(Projects.Common.KOTLIN_UTILS)))
    testImplementation(libs.androidx.paging.common.jvm)

    androidTestImplementation(testFixtures(project(Projects.Common.ANDROID_UTILS)))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.paging.testing) {
        exclude(group = "androidx.paging", module = "paging-common-android")
    }
}