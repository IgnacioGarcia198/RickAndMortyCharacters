import plugins.classloader.Projects

plugins {
    //noinspection JavaPluginLanguageLevel
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id(libs.plugins.kotlin.module.plugin.get().pluginId)
    alias(libs.plugins.com.google.devtools.ksp)
}

dependencies {
    implementation(project(Projects.FeatureCharacters.DATA))
    implementation(project(Projects.FeatureCharacters.DOMAIN))
    implementation(project(Projects.Common.KOTLIN_UTILS))
    implementation(project(Projects.Common.PAGING))

    implementation(libs.androidx.paging.common.jvm)
    implementation(libs.com.google.dagger)
    ksp(libs.com.google.dagger.compiler)

    testImplementation(testFixtures(project(Projects.Common.KOTLIN_UTILS)))
    testImplementation(libs.junit)
    testImplementation(libs.mockk.jvm)
    testImplementation(libs.kotlinx.coroutines.test)
}