# Top-level Gradle settings

pluginManagement {
    repositories {
        google()
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

rootProject.name = "SproutTales"

include(":app")
include(":core-model")
include(":data-store")
include(":tts-engine")
include(":media-playback")
include(":importers:txt-importer")
include(":importers:epub-importer")
include(":picturebook")
include(":tools:generator")
