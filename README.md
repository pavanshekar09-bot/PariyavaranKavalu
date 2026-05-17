# Paryavaran-Kavalu – Smart Waste Reporting App


A student-built Android application designed to encourage civic participation in environmental cleanliness by enabling users to report waste or pollution in their surroundings.

Built using Kotlin and Jetpack Compose, with Firebase integration for backend data storage.

---


## Project Overview

Paryavaran-Kavalu (CleanTheSpot Initiative) is a student-built Android application designed to encourage civic participation in environmental cleanliness by enabling users to report waste or pollution in their surroundings.

Built using Kotlin, Jetpack Compose, and Firebase, this project is part of the CleanTheSpot initiative.

The app aims to promote real-time community-driven environmental responsibility through a simple mobile interface.

## Problem Statement

Improper waste disposal and lack of reporting systems make it difficult to track cleanliness issues in local areas. There is no simple and accessible mobile solution for citizens to report such issues quickly.


## Solution

This application provides a lightweight platform where users can:

- Submit waste reports
- Provide waste type and description
- Store reports in Firebase for structured data collection
- Enable future scalability for tracking and analysis


## Features

  Implemented Features
- Clean and responsive UI using Jetpack Compose
- Waste reporting form (type + description)
- Firebase integration for storing reports
- Basic input validation
- Simple and intuitive navigation flow
 

## Firebase Integration

The app uses Firebase to store waste reports in real time.

Each report includes:

Waste type
Description
Timestamp (if added)
Location (planned enhancement)


---
## Key Highlights

- Clean Jetpack Compose UI
- Real-time form validation
- Simple and intuitive user flow
- Lightweight offline-first prototype
- Beginner-friendly architecture

## Tech Stack

| Tool | Purpose |
|------|---------|
| Kotlin | Primary programming language |
| Jetpack Compose | UI development | 
| Firebase | Backend data Storage |
| Android Studio | IDE |

Data is handled locally during runtime for demonstration purposes.


## Firebase Integration

The app uses Firebase to store waste reports in real time.

Each report contains:

Waste type
Description
Timestamp (if enabled)
Location (planned feature)

---


## How to Run

1. Clone or download this repository
2. Open the project in **Android Studio**
3. Let Gradle sync automatically (or click **Sync Now** if prompted)
4. Connect an Android device or start an emulator
5. Click the **Run** button (▶) to build and launch the app

> Minimum SDK: 24 | Target SDK: 34

## How It Works

User submits report → Data stored in Firebase → UI updates automatically

---

## Folder Structure

```
Paryavaran-Kavalu/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/paryavaranakavalu/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   ├── ReportScreen.kt
│   │   │   │   │   └── components/
│   │   │   ├── res/
│   │   │   │   └── values/
│   │   │   │       ├── strings.xml
│   │   │   │       └── themes.xml
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
└── README.md
```
## Screenshots
Screenshots are included in this submission. Also the application can be run on an emulator or Android device to view all screens.

The application is functional and can be run on any Android device or emulator to view all screens:
- Splash Screen
  ![Splash Screen](https://github.com/pavanshekar09-bot/PariyavaranKavalu/blob/7e1ec187c86cd41115a46e8c4b5abdd81a25f4d6/Open%20screen.jpeg)
- Home Screen
  ![Home Screen](https://github.com/pavanshekar09-bot/PariyavaranKavalu/blob/db346f68c12efdaff20994bcd19c7c7cde95e5b4/Homescreen.jpeg)
- Report Waste Screen
  ![Report Waste Screen](https://github.com/pavanshekar09-bot/PariyavaranKavalu/blob/21270b442defa23ac0f94d197e5066c0ed5aee74/reportscreen.jpeg)
- Profile Screen
  ![Profile Screen](https://github.com/pavanshekar09-bot/PariyavaranKavalu/blob/acf15545d4adb1b4c62bbffa630e33809efdf87a/profilescreen.jpeg)

---
## Project Status

- Core features implemented and working
- UI fully functional using Jetpack Compose
- Local data handling for demonstration
- Ready for evaluation submission

## Future Improvements

- Add Firebase to store reports in a real database
- Allow users to attach a photo of the waste
- Add map view to show report locations
- User login and profile using Firebase Auth
- Push notifications for report status updates

---


## Learning Outcomes

This project helped in understanding:

- Android application development using Kotlin
- Jetpack Compose UI design
- Firebase backend integration
- Real-world problem-solving using mobile technology


## Author

Developed as part of an internship submission under the CleanTheSpot initiative
Built using Kotlin + Jetpack Compose in Android Studio
