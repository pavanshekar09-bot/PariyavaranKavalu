# Paryavaran-Kavalu – Smart Waste Reporting App
Built as a student project for civic environmental reporting and awareness.

A simple Android app built with Kotlin and Jetpack Compose that allows users to report waste or pollution in their surroundings. Designed to encourage community participation in keeping the environment clean.

---

## Features

- Report waste issues by filling a simple form (location name, waste type, description)
- View submitted reports during app session (temporary local data)
- Mark reports as resolved or pending
- Basic input validation with user-friendly feedback
- Clean and minimal UI with Jetpack Compose

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
| Android Studio | IDE |

> No backend or database is currently integrated; data is handled locally during runtime for demonstration purposes.

---

## How to Run

1. Clone or download this repository
2. Open the project in **Android Studio**
3. Let Gradle sync automatically (or click **Sync Now** if prompted)
4. Connect an Android device or start an emulator
5. Click the **Run** button (▶) to build and launch the app

> Minimum SDK: 24 | Target SDK: 34

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
Screenshots are included in this submission. also the application can be run on an emulator or Android device to view all screens.

The application is fully functional and can be run on any Android device or emulator to view all screens:
- Splash Screen
  ![Splash Screen](https://github.com/pavanshekar09-bot/PariyavaranKavalu/blob/7e1ec187c86cd41115a46e8c4b5abdd81a25f4d6/Open%20screen.jpeg)
- Home Screen
  ![Splash Screen](https://github.com/pavanshekar09-bot/PariyavaranKavalu/blob/db346f68c12efdaff20994bcd19c7c7cde95e5b4/Homescreen.jpeg)
- Report Waste Screen
  ![Splash Screen](https://github.com/pavanshekar09-bot/PariyavaranKavalu/blob/21270b442defa23ac0f94d197e5066c0ed5aee74/reportscreen.jpeg)
- Profile Screen
  ![Splash Screen](https://github.com/pavanshekar09-bot/PariyavaranKavalu/blob/acf15545d4adb1b4c62bbffa630e33809efdf87a/profilescreen.jpeg)

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

## Author

Developed as part of a student internship submission.  
Built with Kotlin + Jetpack Compose on Android Studio.
