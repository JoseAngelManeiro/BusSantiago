# Contributing to Bus Santiago

Thank you for your interest in contributing to **Bus Santiago**!  
This document explains how to report issues, propose improvements, and submit pull requests while keeping the project consistent and maintainable.


# 📝 Table of Contents
1. [Code of Conduct](#code-of-conduct)
2. [How to Report Issues](#how-to-report-issues)
3. [Project Structure](#project-structure)
4. [Development Guidelines](#development-guidelines)
5. [Coding Style](#coding-style)
6. [Branching Model](#branching-model)
7. [Pull Request Guidelines](#pull-request-guidelines)
8. [Commit Message Style](#commit-message-style)


## 🧭 Code of Conduct
By participating in this project, you agree to follow the principles of respect, collaboration, and kindness.  
Please keep discussions constructive.


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


## 🎨 Coding Style

### Kotlin style rules:
- Use `Kotlin` idiomatic style.
- Use extension functions where appropriate.
- Limit file size when possible (avoid “god classes”).
- Prefer sealed classes / enums for ViewState.
- Use `internal` visibility to encapsulate module internals.

If you use tools like **Ktlint/Detekt**, contributions should respect their formatting.  
(If they are added to the project in the future, this section will be updated.)


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


## 🙌 Thank You!

Your contributions are greatly appreciated.  
Bus Santiago is a personal but evolving project, and improvements from the community are always welcome.


