# BusSantiago - Development Guide

Welcome to the BusSantiago project! This document provides an overview of the technical stack, architectural patterns, and project structure to help you get started quickly.

## 🛠 Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (with ViewBinding for legacy XML support)
- **Architecture:** [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) + [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel)
- **Dependency Injection:** [Koin](https://insert-koin.io/)
- **Networking:** [Retrofit 2](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- **Asynchronous Work:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **Navigation:** [Jetpack Navigation Component](https://developer.android.com/guide/navigation)
- **Testing:** [JUnit 4](https://junit.org/junit4/), [Mockito](https://site.mockito.org/), [Espresso](https://developer.android.com/training/testing/espresso), [Robolectric](https://robolectric.org/)
- **Build System:** [Gradle](https://gradle.org/) (Kotlin DSL / Version Catalog)
- **CI/CD:** [CircleCI](https://circleci.com/)

## 🏗 Architecture

The project is divided into several modules to ensure separation of concerns and maintainability:

### Modules

- **`:app`**: The main Android application module. Contains the Presentation layer (Fragments, ViewModels, Compose UIs, Navigation).
- **`:core`**: Contains the core business logic.
  - `domain`: Use Cases (Interactors) and Domain Models.
  - `data`: Repositories implementation, API definitions (Retrofit), Caching, and Mappers.
- **`:shared`**: Shared resources and utilities used by multiple modules (e.g., UI components, shared constants).
- **`:widget`**: Android App Widget implementation.

### Layered Architecture (Clean Architecture)

1.  **Presentation Layer (`app` module):** Uses MVVM. Fragments/Compose UI observe changes in ViewModels.
2.  **Domain Layer (`core` module):** Contains the business rules. It is independent of any other layer. It defines `UseCases` that interact with Repositories.
3.  **Data Layer (`core` module):** Implements the Repositories defined in the Domain layer. It handles data sourcing from Network (Retrofit) or Local Cache.

## 📁 Project Structure

```text
BusSantiago/
├── app/                # UI, Fragments, ViewModels, DI Modules
├── core/               # Domain (UseCases) and Data (Repositories, API, Cache)
├── shared/             # Shared UI components and utilities
├── widget/             # Android Widget implementation
├── gradle/             # Gradle Version Catalog (libs.versions.toml)
└── art/                # Documentation assets (diagrams, screenshots)
```

## 🚀 Getting Started

1.  **API Keys:** The project requires a Google Maps API key. You should create an `apikey.properties` file in the root directory (refer to `apikey.properties.template`).
2.  **Building:** Run `./gradlew assembleDebug` to build the application.
3.  **Testing:** Run `./gradlew test` for unit tests and `./gradlew connectedAndroidTest` for instrumented tests.

## 📏 Coding Standards

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).
- Ensure every new feature has accompanying unit tests in the appropriate module.
- Use `libs.versions.toml` for dependency management.
