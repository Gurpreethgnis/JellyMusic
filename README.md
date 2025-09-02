# JellyMusic

A modern Android music player app for Jellyfin servers built with Kotlin, Jetpack Compose, and Material 3.

## Features

- **Multi-module Architecture**: Clean separation with app, data, domain, and player modules
- **Modern UI**: Built with Jetpack Compose and Material 3 design system
- **Jellyfin Integration**: Connect to your Jellyfin server to browse and play music
- **Media3 Player**: Advanced audio playback with ExoPlayer
- **Google Cast Support**: Cast music to Chromecast devices
- **Offline Caching**: Room database for offline-first experience
- **Paging**: Efficient loading of large music libraries
- **Search**: Fast search across artists, albums, and tracks

## Architecture

The project follows Clean Architecture principles with the following modules:

- **app**: UI layer with Compose screens and navigation
- **domain**: Business logic, models, and repository interfaces
- **data**: Data layer with API clients, database, and repository implementations
- **player**: Media playback with Media3 and Cast functionality

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM with Clean Architecture
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp + Kotlinx Serialization
- **Database**: Room + Paging 3
- **Media**: Media3 (ExoPlayer) + Cast SDK
- **Image Loading**: Coil
- **Build System**: Gradle with Version Catalogs
- **Code Quality**: ktlint

## Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34
- JDK 17
- Android device or emulator with API level 24+

## Build Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd JellyMusic
```

### 2. Open in Android Studio

Open the project in Android Studio. The project uses Gradle with version catalogs, so dependencies will be automatically resolved.

### 3. Sync Project

After opening the project, Android Studio will prompt you to sync the project with Gradle files. Click "Sync Now" or go to:
- File → Sync Project with Gradle Files

### 4. Build the Project

#### Debug Build
```bash
./gradlew assembleDebug
```

#### Release Build
```bash
./gradlew assembleRelease
```

### 5. Install on Device

#### Using ADB
```bash
# Enable USB debugging on your device
# Connect device via USB
adb install app/build/outputs/apk/debug/app-debug.apk
```

#### Using Android Studio
1. Connect your device via USB
2. Enable USB debugging
3. Click the "Run" button (green play icon) in Android Studio
4. Select your device and click "OK"

## Project Structure

```
JellyMusic/
├── app/                          # Main application module
│   ├── src/main/
│   │   ├── java/com/jellymusic/app/
│   │   │   ├── navigation/       # Navigation components
│   │   │   └── ui/              # UI components and screens
│   │   └── res/                 # Resources
│   └── build.gradle.kts
├── data/                         # Data layer module
│   ├── src/main/java/com/jellymusic/data/
│   │   ├── local/               # Room database
│   │   ├── remote/              # API clients
│   │   └── repository/          # Repository implementations
│   └── build.gradle.kts
├── domain/                       # Domain layer module
│   ├── src/main/java/com/jellymusic/domain/
│   │   ├── model/               # Domain models
│   │   ├── repository/          # Repository interfaces
│   │   └── usecase/             # Use cases
│   └── build.gradle.kts
├── player/                       # Player module
│   ├── src/main/java/com/jellymusic/player/
│   │   ├── service/             # Media session service
│   │   └── cast/                # Cast functionality
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml       # Version catalog
├── build.gradle.kts             # Root build file
├── settings.gradle.kts          # Project settings
└── README.md
```

## Configuration

### Jellyfin Server Setup

1. Ensure your Jellyfin server is running and accessible
2. Note your server URL (e.g., `http://192.168.1.100:8096`)
3. Create a user account on your Jellyfin server
4. The app will prompt for server URL and credentials on first launch

### Cast Setup

For Google Cast functionality:
1. Ensure your device is on the same network as Chromecast devices
2. Cast devices will be automatically discovered
3. Use the cast button in the player to start casting

## Development

### Code Style

The project uses ktlint for code formatting. To format code:

```bash
./gradlew ktlintFormat
```

To check code style:

```bash
./gradlew ktlintCheck
```

### Adding New Features

1. **Domain Layer**: Add models and repository interfaces
2. **Data Layer**: Implement repository interfaces
3. **App Layer**: Create UI components and screens
4. **Player Layer**: Add media functionality if needed

### Testing

Run tests:

```bash
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # Instrumented tests
```

## Troubleshooting

### Build Issues

1. **Gradle Sync Failed**: 
   - Check internet connection
   - Invalidate caches: File → Invalidate Caches and Restart
   - Clean project: Build → Clean Project

2. **Missing Dependencies**:
   - Sync project with Gradle files
   - Check `gradle/libs.versions.toml` for correct versions

3. **Compilation Errors**:
   - Ensure JDK 17 is set in Project Structure
   - Check Kotlin version compatibility

### Runtime Issues

1. **App Crashes on Launch**:
   - Check logcat for error details
   - Ensure all permissions are granted

2. **Network Issues**:
   - Verify Jellyfin server URL is correct
   - Check network connectivity
   - Ensure server is accessible from device

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests and linting
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- [Jellyfin](https://jellyfin.org/) for the media server platform
- [Android Jetpack](https://developer.android.com/jetpack) for modern Android development tools
- [Material Design](https://material.io/) for design guidelines
