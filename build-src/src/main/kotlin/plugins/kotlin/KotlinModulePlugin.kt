package plugins.kotlin

import BuildConstants
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("APPLYING KOTLIN PLUGIN TO PROJECT: ${target.name}")
        target.extensions.configure<JavaPluginExtension> {
            sourceCompatibility = BuildConstants.JAVA_VERSION
            targetCompatibility = BuildConstants.JAVA_VERSION
        }
        target.tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = BuildConstants.JAVA_VERSION.toString()
            }
        }
    }
}
