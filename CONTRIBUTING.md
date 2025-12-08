# Contributing to Bus Santiago

Thank you for your interest in contributing to **Bus Santiago**!  
This document explains how to report issues, propose improvements, and submit pull requests while keeping the project consistent and maintainable.

&nbsp;

# 📝 Table of Contents
1. [Code of Conduct](#code-of-conduct)
2. [How to Report Issues](#how-to-report-issues)
3. [Project Structure](#project-structure)
4. [Development Setup](#development-setup)
5. [Development Guidelines](#development-guidelines)
6. [Coding Style](#coding-style)
7. [Branching Model](#branching-model)
8. [Pull Request Guidelines](#pull-request-guidelines)
9. [Commit Message Style](#commit-message-style)

&nbsp;

## 🧭 Code of Conduct
By participating in this project, you agree to follow the principles of respect, collaboration, and kindness.  
Please keep discussions constructive.

&nbsp;

## 🐞 How to Report Issues

When opening an issue, please include:

- A clear and descriptive title
- Steps to reproduce the problem (if applicable)
- Expected vs. actual behavior
- Screenshots or logs (if useful)
- Device / Android version information

Feature requests are also welcome.  
If you want to propose a new feature, please describe:

- The motivation behind it
- Why it is useful
- Which module it affects
- Any alternative solutions considered

&nbsp;

## 🧱 Project Structure

Bus Santiago is a modularized Android project with the following modules:

### **core/**
Contains domain logic, repositories, data sources, and use cases.  
Everything except public interfaces and models is `internal`.

### **shared/**
Shared UI utilities, extensions, and resources used by both:
- `app`
- `widget`

### **widget/**
All logic and UI for the Android home-screen widget.

### **app/**
Presentation layer containing Fragments, ViewModels, and Navigation.

&nbsp;

## 🛠️ Development Setup

### Prerequisites
- Android Studio (latest stable version recommended)
- JDK 21
- Android SDK with API 35

### Google Maps Configuration

The app uses Google Maps to display bus stops on a map. To enable this feature locally, you need to configure your own API key:

1. **Copy the template file:**
   ```bash
   cp apikey.properties.template apikey.properties
   ```

2. **Get a Google Maps API key:**
   - Go to [Google Cloud Console](https://console.cloud.google.com/google/maps-apis/credentials)
   - Create a new project (or select an existing one)
   - Enable **Maps SDK for Android**
   - Create an API key under "Credentials"

3. **Add your key to `apikey.properties`:**
   ```properties
   apikeyDebug=YOUR_API_KEY_HERE
   apikeyRelease=YOUR_API_KEY_HERE
   ```

4. **Build and run the app**

> **Note:** The `apikey.properties` file is gitignored and will never be committed. If you don't configure an API key, the app will still build and run, but the map screen will display an error instead of the map tiles.

For more details on API key setup, see the [official documentation](https://developers.google.com/maps/documentation/android-sdk/get-api-key).

&nbsp;

## 🔧 Development Guidelines

### ✔ Follow module boundaries
- **core** must not depend on UI modules.
- **shared** should contain only code that is strictly common.
- **widget** logic must stay isolated and clean.
- **app** must not contain domain or data logic.

### ✔ Avoid adding new third-party dependencies unless necessary
If required, include them via **Version Catalog** in `gradle/libs.versions.toml`.

### ✔ Keep UI code stateless when possible
ViewModels handle state.  
Fragments should remain **thin**.

### ✔ Prefer immutability
Use `val` over `var` unless absolutely required.

### ✔ Follow existing architecture (MVVM + coroutines + Koin)

&nbsp;

## 🎨 Coding Style

### Kotlin style rules:
- Use `Kotlin` idiomatic style.
- Use extension functions where appropriate.
- Limit file size when possible (avoid “god classes”).
- Prefer sealed classes / enums for ViewState.
- Use `internal` visibility to encapsulate module internals.

If you use tools like **Ktlint/Detekt**, contributions should respect their formatting.  
(If they are added to the project in the future, this section will be updated.)

&nbsp;

## 🌱 Branching Model

We use a lightweight Git model:

- **main** → production-ready code
- **feature/*** → new features or improvements
- **fix/*** → bug fixes
- **docs/*** → documentation-only changes

Example:

    feature/add-route-filter  
    fix/crash-on-stop-details  
    docs/update-readme

&nbsp;

## 🔄 Pull Request Guidelines

Before opening a PR:

1. Make sure it builds successfully.
2. Run linting/formatting if applicable.
3. Ensure changes follow module boundaries.
4. Update or add tests if relevant.
5. Keep PRs focused: **one topic per PR**.

### PR description should include:

- A summary of what the change does
- The module(s) affected
- Screenshots for UI changes
- Any technical decisions or trade-offs

### PR size guidelines:
- Prefer small & frequent PRs
- If a PR grows too large, split it into multiple steps

### Reviews:
All PRs will be reviewed by the project maintainer.  
Feedback is expected to be constructive and technical.

&nbsp;

## 🧾 Commit Message Style

Use clear commit messages following this format `<type>: <short description>`.


Types:

- **feat:** new feature
- **fix:** bug fix
- **refactor:** code refactoring without behavior change
- **docs:** documentation updates
- **style:** formatting, code style
- **test:** test-related changes
- **chore:** maintenance tasks

Examples:

    feat: add new UI for stop selection  
    fix: prevent crash when API response is empty  
    docs: update architecture image in README

&nbsp;

## 🙌 Thank You!

Your contributions are greatly appreciated.  
Bus Santiago is a personal but evolving project, and improvements from the community are always welcome.


