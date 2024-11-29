package plugins.classloader

import org.gradle.api.Plugin
import org.gradle.api.Project

class ClassLoaderPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // no-op
    }
}

object Projects {
    object Features {
        object Characters {
            const val UI = ":features:feature-characters:ui"
            const val DOMAIN = ":features:feature-characters:domain"
            const val DATA = ":features:feature-characters:data"
            const val PRESENTATION = ":features:feature-characters:presentation"
            const val DATA_PAGING = ":features:feature-characters:data:paging"
        }

        object NetworkMonitor {
            const val UI = ":features:feature-network-monitor:ui"
            const val DOMAIN = ":features:feature-network-monitor:domain"
            const val DATA = ":features:feature-network-monitor:data"
            const val PRESENTATION = ":features:feature-network-monitor:presentation"
        }

        object Auth {
            const val UI = ":features:feature-auth:ui"
            const val PRESENTATION = ":features:feature-auth:presentation"
            const val AUTH = ":features:feature-auth:auth"
            const val DOMAIN = ":features:feature-auth:domain"
        }

        object MainNavigation {
            const val UI = ":features:main-navigation:ui"
        }
    }

    object Framework {
        const val LOCAL = ":framework:local"
        const val REMOTE = ":framework:remote"
        const val DI_CONNECTOR = ":framework:di-connector"
    }

    object Common {
        const val ANDROID_UTILS = ":common:android-utils"
        const val KOTLIN_UTILS = ":common:kotlin-utils"
        const val UI_COMMON = ":common:ui-common"
        const val RESOURCES = ":common:resources"
        const val PAGING = ":common:paging"
        const val NETWORK = ":common:network"
    }
}
