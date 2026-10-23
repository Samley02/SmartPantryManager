# SmartPantryManager

A mobile app to manage pantry ingredients and suggest recipes based on what you have.

## Description
Users can add, view, update and delete pantry items. The app suggests recipes only when ALL required ingredients are available in the pantry (strict matching).

## Database Choice
I chose SQLite with SQLiteOpenHelper because it works offline, is lightweight, built into Android, and persists data after closing the app - ideal for pantry storage.

## Setup / Run Instructions
1. Clone repo: https://github.com/Samley02/SmartPantryManager.git
2. Open in Android Studio Ladybug or newer
3. Sync Gradle
4. Run on emulator or physical device (Android 7.0+)

## Features
- CRUD for pantry items
- Strict recipe matching
- RecyclerView with Adapter
- Data persistence