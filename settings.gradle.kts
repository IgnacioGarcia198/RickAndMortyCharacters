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
include(":features:characters:ui")
include(":features:characters:domain")
include(":features:characters:data")
include(":features:characters:presentation")
include(":features:characters:data:paging")

// common modules
include(":common:android-utils")
include(":common:kotlin-utils")
include(":common:ui-common")
include(":common:resources")
include(":common:paging")
include(":common:network")
// build
includeBuild("common/build-src")

// framework
include(":framework:local")
include(":framework:remote")
include(":framework:di-connector")

include(":features:network-monitor:presentation")
include(":features:network-monitor:ui")
include(":features:network-monitor:data")
include(":features:network-monitor:domain")
include(":features:auth:ui")
include(":features:auth:presentation")
include(":features:auth:auth")
include(":features:auth:domain")
include(":features:main-navigation:ui")
