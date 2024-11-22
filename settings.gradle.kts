pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RickAndMorty"
include(":app")
include(":feature-characters:ui")
include(":feature-characters:domain")
include(":feature-characters:data")
include(":framework:local")
include(":feature-characters:presentation")

// common modules
include(":android-utils")
include(":kotlin-utils")
include(":ui-common")
include(":resources")

// build
includeBuild("build-src")
include(":framework:remote")
include(":framework:di-connector")
include(":feature-characters:data:paging")
include(":paging")
include(":network")
include(":feature-network-monitor:presentation")
include(":feature-network-monitor:ui")
include(":feature-network-monitor:data")
include(":feature-network-monitor:domain")
