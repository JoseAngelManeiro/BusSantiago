
# Bus Santiago

Bus Santiago is an Android application that provides **real-time city bus arrival times** for Santiago de Compostela.  
It is a personal side-project, published on Google Play, built with **Clean Architecture**, **modularization**, and modern Android development practices.

<p>
<kbd>
<img src="./art/ApplicationVideo.gif" width="280" alt="Bus Santiago"/>
</kbd>
</p>



## 📐 Architecture Overview

The project is structured into **four independent modules**, each with a clear responsibility boundary.  
It follows **MVVM**, Android Architecture Components, **coroutines**, and dependency injection with **Koin**.

<p>
<kbd>  
<img src="./art/app_architecture.png" width="540" alt="App Architecture"/>  
</kbd>
</p>

### 🧱 Modules

#### **core**
Module responsible for **domain logic** and **data access**.  
It exposes only the models and interfaces located under `org.galio.bussantiago.core`.


All implementation details (repositories, data sources, use cases…) are kept **internal** to enforce a clean separation of concerns.

---

#### **shared**
A set of **shared presentation utilities**, **extensions**, and **resources** used by both the `app` and the `widget` modules.  
Its goal is to avoid duplication and keep UI logic consistent.

---

#### **widget**
Contains all logic and layouts required for the **Android home-screen widget**, isolated from the main app to keep boundaries clean.

---

#### **app**
This module contains the **presentation layer**.  
It follows a **Fragment ↔ ViewModel** structure and uses:

- `LiveData` for lifecycle-aware state observation
- `InteractorExecutor` to execute domain use cases inside a coroutine scope
- Android Navigation Component (single-activity setup)

The `ViewModel` exposes UI state and handles business operations, while Fragments remain **thin observers**.


## 🧩 Dependency & Version Management (Version Catalog)

The project uses **Gradle Version Catalog** with the file `gradle/libs.versions.toml`.

It centralizes:

- Library versions
- Plugin versions
- Dependency bundles
- Aliases for common imports

This improves maintainability and ensures consistent dependency upgrades across all modules.


## 🧪 Tech Stack

- **Kotlin**
- Android Architecture Components (ViewModel, LiveData, Navigation)
- **Coroutines**
- **Koin** for dependency injection
- Clean modular architecture
- Widgets API
- Version Catalog with TOML
- Material Components

> _Note: A migration toward Jetpack Compose + newer DI options may be considered in the future._

---

## 🎯 Project Goals

- Provide reliable real-time bus information for Santiago de Compostela
- Maintain a clean, scalable structure suitable for long-term personal development
- Serve as an open-source reference project for architectural clarity and modularization in a real production app


## 🔧 Building the Project

1. Clone the repository
2. Open it in **Android Studio**
3. Let Gradle sync (Version Catalog is applied automatically)
4. Run the `app` module on any device with API level 26+

## 🤝 Contributing

Contributions are welcome!  
Please see `CONTRIBUTING.md` for guidelines on:

- Opening issues
- Coding style
- Module boundaries
- PR process


## 📄 License

This project is licensed under the **GNU General Public License v3.0 (GPLv3)**.  
See the `LICENSE` file in the root of this repository for full details.


## 📎 Links

- **Google Play:** [Bus Santiago][bus-santiago]
- Architecture Components: [Documentation][architecture]
- Koin: [Documentation][koin]

[bus-santiago]: https://play.google.com/store/apps/details?id=org.galio.bussantiago&hl=es
[architecture]: https://developer.android.com/topic/libraries/architecture
[koin]: https://insert-koin.io/


