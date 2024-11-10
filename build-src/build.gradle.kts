plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.com.android.tools.build.gradle)
    implementation(libs.com.android.tools.build.gradle.api)
    implementation(libs.kotlin.gradle.plugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("android-module-plugin") {
            id = "android-module-plugin"
            implementationClass = "plugins.android.AndroidModulePlugin"
        }

        register("jetpack-compose-plugin") {
            id = "jetpack-compose-plugin"
            implementationClass = "plugins.android.ComposePlugin"
        }

        register("kotlin-module-plugin") {
            id = "kotlin-module-plugin"
            implementationClass = "plugins.kotlin.KotlinModulePlugin"
        }
    }
}
