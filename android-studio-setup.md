# Android Studio JDK Image Transformation Fix

## Problem
Android Studio uses its own Gradle daemon and build process, which may not pick up the project's `gradle.properties` settings, causing JDK image transformation errors.

## Solution

### Method 1: Configure Android Studio JVM Arguments

1. **Open Android Studio**
2. **Go to File → Settings (or Android Studio → Preferences on Mac)**
3. **Navigate to Build, Execution, Deployment → Build Tools → Gradle**
4. **In "Gradle JDK" section, click "Edit" next to "Gradle JVM"**
5. **Add these JVM arguments to "VM options":**
   ```
   -Dandroid.enableJdkImageTransform=false
   -Dorg.gradle.android.enableJdkImageTransform=false
   -Dandroid.enableR8.fullMode=false
   ```

### Method 2: Use Command Line Build (Recommended)

Instead of building in Android Studio, use the command line:

```bash
# Clean build
./gradlew-with-jdk-fix.sh clean

# Build debug APK
./gradlew-with-jdk-fix.sh assembleDebug

# Build specific module
./gradlew-with-jdk-fix.sh app:assembleDebug
```

### Method 3: Configure Android Studio to Use Project Gradle Wrapper

1. **In Android Studio, go to File → Settings → Build Tools → Gradle**
2. **Set "Use Gradle from" to "gradle-wrapper.properties"**
3. **This ensures Android Studio uses the project's Gradle wrapper with our custom settings**

### Method 4: Restart Android Studio

After making changes:
1. **Close Android Studio completely**
2. **Delete Android Studio caches:**
   - Windows: `%APPDATA%\Google\AndroidStudio4.1\caches`
   - Mac: `~/Library/Caches/AndroidStudio4.1`
   - Linux: `~/.cache/AndroidStudio4.1`
3. **Restart Android Studio**

## Verification

After applying these fixes, try building in Android Studio. The JDK image transformation error should be resolved.

## Alternative: Use Command Line Only

If Android Studio continues to have issues, you can:
1. **Use command line for builds** (recommended)
2. **Use Android Studio only for code editing and debugging**
3. **Run builds via terminal or CI/CD**

The command line build is more reliable and uses our custom wrapper script with all the necessary JDK image transformation disable settings.
