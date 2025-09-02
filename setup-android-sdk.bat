@echo off
echo 🔧 Android SDK Setup for JellyMusic
echo =====================================

REM Check common Android SDK locations
set "SDK_FOUND="
set "SDK_PATH="

REM Check Android Studio installation
if exist "%PROGRAMFILES%\Android\Android Studio\Sdk" (
    set "SDK_PATH=%PROGRAMFILES%\Android\Android Studio\Sdk"
    set "SDK_FOUND=1"
    echo ✅ Android SDK found at: %SDK_PATH%
) else if exist "%LOCALAPPDATA%\Android\Sdk" (
    set "SDK_PATH=%LOCALAPPDATA%\Android\Sdk"
    set "SDK_FOUND=1"
    echo ✅ Android SDK found at: %SDK_PATH%
) else if exist "C:\Android\Sdk" (
    set "SDK_PATH=C:\Android\Sdk"
    set "SDK_FOUND=1"
    echo ✅ Android SDK found at: %SDK_PATH%
)

if defined SDK_FOUND (
    echo ✅ Android SDK found!
    
    REM Update local.properties
    powershell -Command "(Get-Content 'local.properties') -replace 'sdk\.dir=.*', 'sdk.dir=%SDK_PATH:\=\\%' | Set-Content 'local.properties'"
    echo ✅ Updated local.properties with SDK path: %SDK_PATH%
    
    REM Set ANDROID_HOME environment variable
    setx ANDROID_HOME "%SDK_PATH%"
    echo ✅ Set ANDROID_HOME environment variable
    
    echo.
    echo 🎉 Setup complete! You can now run:
    echo    ./gradlew build
    echo    ./gradlew data:test
) else (
    echo ❌ Android SDK not found!
    echo.
    echo 📋 Please install Android SDK:
    echo 1. Download Android Studio from: https://developer.android.com/studio
    echo 2. Install with default settings
    echo 3. Run this script again
    echo.
    echo Or install command line tools only:
    echo 1. Download from: https://developer.android.com/studio#command-tools
    echo 2. Extract to C:\Android\Sdk
    echo 3. Run this script again
)

echo.
pause
