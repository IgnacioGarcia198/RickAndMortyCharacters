package plugins.android

import BuildConstants
import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class AndroidModulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libs = project.the<LibrariesForLibs>()
        println("APPLYING ANDROID PLUGIN TO PROJECT: ${project.name}")
        println("JAVA VERSION: ${BuildConstants.JAVA_VERSION}")

        project.extensions.configure<BaseExtension> {
            compileSdkVersion(libs.versions.compileSdk.get().toInt())
            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
                targetSdk = libs.versions.targetSdk.get().toInt()
                versionCode = libs.versions.versionCode.get().toInt()
                versionName = libs.versions.versionName.get()

                testInstrumentationRunner = BuildConstants.TEST_INSTRUMENTATION_RUNNER
                consumerProguardFiles("consumer-rules.pro")
            }

            // Common build types for all Android projects
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
                getByName("debug") {
                    isMinifyEnabled = false
                }
            }
            compileOptions {
                sourceCompatibility = BuildConstants.JAVA_VERSION
                targetCompatibility = BuildConstants.JAVA_VERSION
            }

            configureKotlinOptions()
        }
    }

    private fun BaseExtension.configureKotlinOptions() {
        (this as ExtensionAware).extensions.configure<KotlinJvmOptions>("kotlinOptions") {
            jvmTarget = BuildConstants.JAVA_VERSION.toString()
        }
    }
}
