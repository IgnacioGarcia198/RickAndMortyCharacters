package plugins.classloader

import org.gradle.api.Plugin
import org.gradle.api.Project

class ClassLoaderPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // no-op
    }
}

object Projects {
    object FeatureCharacters {
        const val UI = ":feature-characters:ui"
        const val DOMAIN = ":feature-characters:domain"
        const val DATA = ":feature-characters:data"
        const val PRESENTATION = ":feature-characters:presentation"
        const val DATA_PAGING = ":feature-characters:data:paging"
    }

    object FeatureNetworkMonitor {
        const val UI = ":feature-network-monitor:ui"
        const val DOMAIN = ":feature-network-monitor:domain"
        const val DATA = ":feature-network-monitor:data"
        const val PRESENTATION = ":feature-network-monitor:presentation"
    }

    object FeatureAuth {
        const val UI = ":feature-auth:ui"
        const val PRESENTATION = ":feature-auth:presentation"
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
    const val NETWORK = ":network"
}
