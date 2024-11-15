package plugins.android

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the

class ComposePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libs = project.the<LibrariesForLibs>()
        project.extensions.configure<BaseExtension> {
            when (this) {
                is LibraryExtension -> {
                    buildFeatures {
                        compose = true
                    }
                }

                is ApplicationExtension -> {
                    buildFeatures {
                        compose = true
                    }
                }
            }
            composeOptions {
                kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtension.get()
            }
        }
    }
}
