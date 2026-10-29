# Simple Calculator

A clean, modern, and reliable native Android calculator application built with Java and XML Views.

[![Language](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com)
[![Build Status](https://img.shields.io/badge/Build-Passing-success.svg)](#)

---

## 📱 Application Overview

**Simple Calculator** is a fast, responsive, and easy-to-use Android calculator designed for everyday arithmetic operations. Built following standard Android UI guidelines and software separation concerns, it features a clean two-line expression display, rounded touch controls, and high-precision `BigDecimal` arithmetic.

---

## ✨ Features

- **Basic Arithmetic Operations**: Support for addition (`+`), subtraction (`−`), multiplication (`×`), and division (`÷`).
- **Precision Calculations**: Uses Java `BigDecimal` with rounding (`RoundingMode.HALF_UP`) to eliminate common floating-point rounding errors (e.g. `0.1 + 0.2` accurately results in `0.3`).
- **Chained & Subsequent Calculations**: Continue calculations using prior results or chain multiple operations together (e.g., `12 + 8 × 2`).
- **Repeated Equals Operations**: Pressing `=` repeatedly repeats the last operator and operand sequence.
- **Safe Error Handling**: Prevents application crashes on division by zero by displaying a user-friendly `"Cannot divide by zero"` message.
- **Decimal Support**: Automatic decimal formatting with trailing zero removal (`5.0` displays as `5`), preventing duplicate decimal points in a single operand.
- **Special Function Keys**:
  - `AC` (All Clear): Resets the calculator state and display.
  - `DEL` (Delete): Erases the last entered digit or character.
  - `±` (Sign Toggle): Switches numbers between positive and negative values.
  - `%` (Percentage): Instantly converts the entered number to its percentage representation (`val / 100`).

---

## 🎨 UI & User Experience Design

- **Modern Minimalist Theme**: Light background paired with dark typography for optimal legibility.
- **Visual Button Hierarchy**:
  - **Numeric Keys**: Crisp white background with dark bold text.
  - **Operator Keys**: Light blue background with primary blue text.
  - **Action Keys (`AC`, `DEL`, `%`)**: Subtle grey background.
  - **Equals Key (`=`)**: Solid blue background with white text for clear prominence.
- **Rounded Tactile Buttons**: Built with standard ripple drawables (`16dp` corner radii) ensuring comfortable touch targets.
- **Dual Display Area**:
  - **Top Text View**: Displays the active calculation expression (e.g., `125 × 4`).
  - **Main Text View**: Displays the current user input or evaluation result (e.g., `500`).

---

## 🛠️ Architecture & Tech Stack

- **Programming Language**: Java
- **UI Framework**: Android XML View System (`activity_main.xml`, `LinearLayout`, custom XML ripple drawables)
- **Minimum SDK**: Preserved default project configuration (Android 7.0 / SDK 24+)
- **Build System**: Gradle with Kotlin DSL (`build.gradle.kts`)
- **Software Design**:
  - `MainActivity.java`: Manages view binding, event listeners, system bar window insets, and UI updates.
  - `CalculatorLogic.java`: Dedicated calculation engine managing state, input processing, and mathematical operations independently from Android UI APIs.

---

## 📂 Project Structure

```
SimpleCalculator/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/simplecalculator/
│   │   │   │   ├── MainActivity.java        # UI activity & event handling
│   │   │   │   └── CalculatorLogic.java     # Arithmetic state machine & engine
│   │   │   ├── res/
│   │   │   │   ├── drawable/                # Button ripple backgrounds (btn_*_bg.xml)
│   │   │   │   ├── layout/activity_main.xml # Main screen XML layout
│   │   │   │   └── values/                  # Colors, strings, and themes
│   │   │   └── AndroidManifest.xml
│   │   └── test/java/com/example/simplecalculator/
│   │       └── ExampleUnitTest.java         # 15 automated JUnit test cases
│   └── build.gradle.kts
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 🚀 Building and Running

### Prerequisites
- **Android Studio**: Ladybug / 2024.2+ recommended.
- **JDK**: Java 17 or higher.
- **Android SDK**: API Level 34 / 35.

### Steps
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/pAiN889/SimpleCalculator.git
   cd SimpleCalculator
   ```
2. **Open in Android Studio**:
   - Launch Android Studio, choose **Open**, and select the `SimpleCalculator` project directory.
3. **Build the Application**:
   - Run via Gradle terminal:
     ```bash
     ./gradlew assembleDebug
     ```
4. **Run Unit Tests**:
   - Execute all 15 automated logic tests:
     ```bash
     ./gradlew testDebugUnitTest
     ```

---

## 🧪 Testing & Verification

The project includes an automated test suite (`ExampleUnitTest.java`) covering core mathematical requirements and edge cases:

| Test Case | Expression / Action | Expected Result | Status |
|---|---|---|---|
| Addition | `2 + 3 =` | `5` | PASS |
| Subtraction | `9 − 4 =` | `5` | PASS |
| Multiplication | `6 × 7 =` | `42` | PASS |
| Division | `20 ÷ 4 =` | `5` | PASS |
| Decimal Operations | `0.5 + 0.25 =` | `0.75` | PASS |
| Division by Zero | `10 ÷ 0 =` | `"Cannot divide by zero"` | PASS |
| Percentage | `50 %` | `0.5` | PASS |
| Sign Toggle | `5 ±` | `-5` | PASS |
| Backspace | `123 DEL` | `12` | PASS |
| All Clear | `12 + AC` | `0` | PASS |
| Continuation | `15 + 5 = 20 + 2 =` | `22` | PASS |
| Decimal Filtering | `1.2.3` | `1.23` | PASS |
| Operator Overwriting | `12 + × 2 =` | `24` | PASS |
| Direct Equals | `12 =` | `12` | PASS |
| Repeated Equals | `15 + 5 = =` | `25` | PASS |

---

## 📄 License & Repository

Connected to GitHub repository: [https://github.com/pAiN889/SimpleCalculator](https://github.com/pAiN889/SimpleCalculator)
