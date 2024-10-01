rootProject.name = "CryptoCoins"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":app")
include(":common:logger")
include(":common:utils")
include(":core:dispatchers")
include(":core:network")
include(":core:ui")
include(":data:coins")
include(":domain:coins")
include(":feature:coin-list:api")
include(":feature:coin-list:impl")
include(":feature:home:api")
include(":feature:home:impl")
include(":feature:coin-details:api")
include(":feature:coin-details:impl")
