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
include(":features:feature-characters:ui")
include(":features:feature-characters:domain")
include(":features:feature-characters:data")
include(":framework:local")
include(":features:feature-characters:presentation")

// common modules
include(":common:android-utils")
include(":common:kotlin-utils")
include(":common:ui-common")
include(":common:resources")

// build
includeBuild("common/build-src")
include(":framework:remote")
include(":framework:di-connector")
include(":features:feature-characters:data:paging")
include(":common:paging")
include(":common:network")
include(":features:feature-network-monitor:presentation")
include(":features:feature-network-monitor:ui")
include(":features:feature-network-monitor:data")
include(":features:feature-network-monitor:domain")
include(":features:feature-auth:ui")
include(":features:feature-auth:presentation")
include(":features:feature-auth:auth")
include(":features:feature-auth:domain")
include(":features:main-navigation:ui")
