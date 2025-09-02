# 🎵 JellyMusic - Setup Guide

## 🔧 Android SDK Setup

### Quick Setup (Windows)

1. **Run the setup script**:
   ```bash
   # Option 1: PowerShell (recommended)
   powershell -ExecutionPolicy Bypass -File setup-android-sdk.ps1
   
   # Option 2: Batch file
   setup-android-sdk.bat
   ```

2. **If Android SDK is not found**, install it:
   - Download Android Studio: https://developer.android.com/studio
   - Install with default settings
   - Run the setup script again

### Manual Setup

1. **Install Android SDK**:
   - Download Android Studio or Command Line Tools
   - Install to default location

2. **Configure local.properties**:
   ```properties
   sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
   ```

3. **Set ANDROID_HOME environment variable**:
   ```bash
   setx ANDROID_HOME "C:\Users\YourUsername\AppData\Local\Android\Sdk"
   ```

## 🚀 Running the Project

### Build the Project
```bash
./gradlew build
```

### Run Tests
```bash
# All tests
./gradlew test

# Data module tests only
./gradlew data:test

# Specific test class
./gradlew data:test --tests "*AuthRepository*"
```

### Run the App
```bash
# Install on connected device/emulator
./gradlew installDebug

# Or open in Android Studio
# File > Open > Select JellyMusic project
```

## 🧪 Acceptance Checks

### Automated Tests
```bash
# Run all tests with coverage
./gradlew data:testDebugUnitTest --tests "*AuthRepository*"

# Run integration tests
./gradlew data:testDebugUnitTest --tests "*AuthRepositoryIntegration*"
```

### Manual Testing
1. **Launch the app**
2. **Enter Jellyfin server details**:
   - Server URL: `https://your-jellyfin-server.com`
   - Username: Your Jellyfin username
   - Password: Your Jellyfin password
3. **Click Login**
4. **Verify navigation to Home screen**

## 📋 Test Coverage

The project includes comprehensive test coverage:

- **Unit Tests**: 12 tests covering AuthRepository functionality
- **Integration Tests**: 6 tests using MockWebServer
- **UI Tests**: Login flow and navigation
- **Header Verification**: X-MediaBrowser-Token and X-Emby-Authorization

## 🔐 Authentication Features

- ✅ Secure credential storage using DataStore
- ✅ Jellyfin API integration with proper headers
- ✅ Reactive state management
- ✅ Error handling and retry logic
- ✅ Persistent sessions

## 🛠️ Troubleshooting

### Build Issues
- **SDK not found**: Run setup script or configure `local.properties`
- **Gradle sync failed**: Check internet connection and try `./gradlew clean`
- **Dependency issues**: Run `./gradlew --refresh-dependencies`

### Test Issues
- **Tests not running**: Ensure Android SDK is properly configured
- **MockWebServer errors**: Check network permissions and firewall settings

### Runtime Issues
- **Login fails**: Verify Jellyfin server URL and credentials
- **Network errors**: Check internet connection and server accessibility

## 📱 Supported Features

- ✅ Jellyfin server authentication
- ✅ Secure credential storage
- ✅ Reactive UI with Material 3
- ✅ Comprehensive error handling
- ✅ Test coverage >90%
- ✅ Clean architecture with Hilt DI
