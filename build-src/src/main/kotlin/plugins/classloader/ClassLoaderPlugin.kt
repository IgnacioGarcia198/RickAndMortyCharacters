package plugins.classloader

import org.gradle.api.Plugin
import org.gradle.api.Project

class ClassLoaderPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("APPLYING PLUGIN TO PROJECT: ${target.name}")
        // no-op
    }
}

object Projects {
    const val APP = ":app"

    object FeatureCharacters {
        const val UI = ":feature-characters:ui"
        const val DOMAIN = ":feature-characters:domain"
        const val DATA = ":feature-characters:data"
        const val PRESENTATION = ":feature-characters:presentation"
        const val DATA_PAGING = ":feature-characters:data:paging"
    }

    object Framework {
        const val LOCAL = ":framework:local"
        const val REMOTE = ":framework:remote"
        const val DI_CONNECTOR = ":framework:di-connector"
    }

    object Common {
        const val ANDROID_UTILS = ":android-utils"
        const val KOTLIN_UTILS = ":kotlin-utils"
        const val UI_COMMON = ":ui-common"
        const val RESOURCES = ":resources"
    }

    const val PAGING = ":paging"
}
