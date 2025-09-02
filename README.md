# JellyMusic

A modern Android music player app for Jellyfin media servers, built with Kotlin, Jetpack Compose, and Material 3.

## Features

- **Modern UI**: Built with Jetpack Compose and Material 3
- **Jellyfin Integration**: Connect to your Jellyfin media server
- **Authentication**: Secure login with credential storage
- **Multi-module Architecture**: Clean separation of concerns
- **Dependency Injection**: Hilt for clean architecture
- **Networking**: Retrofit with OkHttp for API calls
- **Local Storage**: DataStore for secure credential storage

## Architecture

```
JellyMusic/
├── app/          # Main application module
├── data/         # Data layer (API, database, repositories)
├── domain/       # Domain layer (models, use cases)
└── player/       # Media player module
```

## Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 34
- JDK 17
- Kotlin 1.9.21

## Setup

### 1. Clone the Repository

```bash
git clone https://github.com/Gurpreethgnis/JellyMusic.git
cd JellyMusic
```

### 2. Configure Android SDK

The project includes automated scripts to configure your Android SDK:

**Windows:**
```bash
setup-android-sdk.bat
```

**PowerShell:**
```powershell
.\setup-android-sdk.ps1
```

**Manual Setup:**
Create `local.properties` in the project root:
```properties
sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
```

### 3. Build the Project

**Important**: This project requires a special build command due to JDK image transformation issues.

**Option 1: Use the provided wrapper scripts (Recommended)**

**Windows:**
```bash
gradlew-with-jdk-fix.bat app:assembleDebug
```

**Unix/Linux/macOS:**
```bash
./gradlew-with-jdk-fix.sh app:assembleDebug
```

**Option 2: Use command-line flag**
```bash
./gradlew app:assembleDebug -Dandroid.enableJdkImageTransform=false
```

**Option 3: Build in Android Studio**
1. Open the project in Android Studio
2. Add `-Dandroid.enableJdkImageTransform=false` to VM options in Run/Debug Configurations
3. Build and run

## Project Structure

### App Module (`app/`)
- Main activity and application class
- UI components and screens
- Navigation setup
- Hilt ViewModels

### Data Module (`data/`)
- Jellyfin API client (Retrofit)
- Repository implementations
- Data mappers
- Local storage (DataStore)

### Domain Module (`domain/`)
- Domain models
- Repository interfaces
- Use cases (future)

### Player Module (`player/`)
- Media3/ExoPlayer integration (future)

## Key Technologies

- **UI**: Jetpack Compose, Material 3
- **Architecture**: MVVM with Clean Architecture
- **DI**: Hilt
- **Networking**: Retrofit, OkHttp
- **Serialization**: Gson
- **Local Storage**: DataStore
- **Testing**: MockK, MockWebServer, Turbine

## Troubleshooting

### JDK Image Transformation Error

If you encounter the error:
```
Execution failed for task ':data:compileDebugJavaWithJavac'.
> Could not resolve all files for configuration ':data:androidJdkImage'.
```

**Solution**: Use the provided wrapper scripts or add the system property:
```bash
./gradlew-with-jdk-fix.sh [your-command]
```

### Android SDK Not Found

If you get "SDK location not found":
1. Run the setup script: `setup-android-sdk.bat` (Windows) or `./setup-android-sdk.ps1` (PowerShell)
2. Or manually create `local.properties` with your SDK path

### Build Failures

1. Clean the project: `./gradlew clean`
2. Use the JDK fix wrapper: `./gradlew-with-jdk-fix.sh clean build`
3. Check Android SDK configuration

## Development

### Running Tests

```bash
# Run all tests
./gradlew-with-jdk-fix.sh test

# Run specific module tests
./gradlew-with-jdk-fix.sh data:test
./gradlew-with-jdk-fix.sh app:test
```

### Code Style

The project uses ktlint for code formatting:
```bash
./gradlew-with-jdk-fix.sh ktlintFormat
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test with the JDK fix wrapper
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
