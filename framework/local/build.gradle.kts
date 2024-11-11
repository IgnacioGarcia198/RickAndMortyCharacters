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
    implementation(project(":feature-characters:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // paging
    //implementation(libs.androidx.paging.runtime)
    //implementation(libs.androidx.paging.common.jvm)
    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging) {
        exclude(group = "androidx.paging", module = "paging-common-android")
    }
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.jvm)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(testFixtures(project(":kotlin-utils")))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}