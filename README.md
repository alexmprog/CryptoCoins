# CryptoCoins

![GitHub Logo](/screenshots/android_app_flow.gif)

## About
Compose Multiplatform app which shows CryptoMarket coin prices.
* User real  [CoinGecko](https://docs.coingecko.com/reference/introduction) api.<br>
* Clean and Simple Material UI.<br>
* Use multi-module Gradle architecture.<br>

## Built With 🛠
[Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.<br>
[Kotlin Gradle DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Provides an alternative syntax to the traditional Groovy DSL for Gradle build system. <br>
[Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Kotlin Asynchronous or non-blocking programming.<br>
[Compose](https://developer.android.com/develop/ui/compose/documentation) - The modern toolkit for building native Android UI.<br>
[Decompose](https://arkivanov.github.io/Decompose/) - Kotlin Multiplatform library for breaking down your code into lifecycle-aware business logic components.<br>
[Kodein](https://kosi-libs.org/kodein/7.22/index.html) - Useful dependency injection / retrieval container for Kotlin.<br>
[Ktor](https://ktor.io) - A type-safe Kotlin Multiplatform HTTP client.<br>
[Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)) - A modern JSON library for Kotlin and Java.<br>

## Module Graph

```mermaid
%%{
  init: {
    'theme': 'base',
    'themeVariables': {"primaryTextColor":"#F6F8FAff","primaryColor":"#5a4f7c","primaryBorderColor":"#5a4f7c","tertiaryColor":"#40375c","lineColor":"#f5a623","fontSize":"12px"}
  }
}%%

graph TB
  subgraph :common
    :common:utils["utils"]
    :common:logger["logger"]
  end
  subgraph :core
    :core:dispatchers["dispatchers"]
    :core:network["network"]
    :core:ui["ui"]
  end
  subgraph :data
    :data:coins["coins"]
  end
  subgraph :domain
    :domain:coins["coins"]
  end
  subgraph :feature:coin-details
    :feature:coin-details:api["api"]
    :feature:coin-details:impl["impl"]
  end
  subgraph :feature:coin-list
    :feature:coin-list:api["api"]
    :feature:coin-list:impl["impl"]
  end
  subgraph :feature:home
    :feature:home:impl["impl"]
    :feature:home:api["api"]
  end
  :feature:coin-details:api --> :domain:coins
  :feature:home:impl --> :domain:coins
  :feature:home:impl --> :feature:coin-details:api
  :feature:home:impl --> :feature:coin-list:api
  :feature:home:impl --> :feature:home:api
  :data:coins --> :common:utils
  :data:coins --> :core:dispatchers
  :data:coins --> :core:network
  :data:coins --> :domain:coins
  :app --> :common:logger
  :app --> :core:dispatchers
  :app --> :core:network
  :app --> :core:ui
  :app --> :data:coins
  :app --> :domain:coins
  :app --> :feature:coin-details:api
  :app --> :feature:coin-details:impl
  :app --> :feature:coin-list:api
  :app --> :feature:coin-list:impl
  :app --> :feature:home:api
  :app --> :feature:home:impl
  :feature:coin-details:impl --> :common:utils
  :feature:coin-details:impl --> :core:dispatchers
  :feature:coin-details:impl --> :core:ui
  :feature:coin-details:impl --> :domain:coins
  :feature:coin-details:impl --> :feature:coin-details:api
  :feature:coin-list:impl --> :core:ui
  :feature:coin-list:impl --> :domain:coins
  :feature:coin-list:impl --> :feature:coin-list:api
  :domain:coins --> :common:utils
  :feature:home:api --> :domain:coins
  :feature:home:api --> :feature:coin-list:api
  :feature:home:api --> :feature:coin-details:api
  :core:network --> :common:logger
  :core:network --> :common:utils
  :core:ui --> :common:utils
  :feature:coin-list:api --> :domain:coins

classDef kotlin-multiplatform fill:#C792EA,stroke:#fff,stroke-width:2px,color:#fff;
classDef android-application fill:#2C4162,stroke:#fff,stroke-width:2px,color:#fff;
class :feature:coin-details:api kotlin-multiplatform
class :domain:coins kotlin-multiplatform
class :feature:home:impl kotlin-multiplatform
class :feature:coin-list:api kotlin-multiplatform
class :feature:home:api kotlin-multiplatform
class :data:coins kotlin-multiplatform
class :common:utils kotlin-multiplatform
class :core:dispatchers kotlin-multiplatform
class :core:network kotlin-multiplatform
class :app android-application
class :common:logger kotlin-multiplatform
class :core:ui kotlin-multiplatform
class :feature:coin-details:impl kotlin-multiplatform
class :feature:coin-list:impl kotlin-multiplatform

```
## Architecture
This repository uses recommended Android [App architecture](https://developer.android.com/topic/architecture).
![Image of Clean Architecture](https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview.png)