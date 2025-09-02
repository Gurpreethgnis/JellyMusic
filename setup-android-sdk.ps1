# Android SDK Setup Script for JellyMusic Project
# Run this script as Administrator

Write-Host "🔧 Android SDK Setup for JellyMusic" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green

# Check if Android Studio is installed
$androidStudioPaths = @(
    "${env:ProgramFiles}\Android\Android Studio",
    "${env:ProgramFiles(x86)}\Android\Android Studio",
    "${env:LOCALAPPDATA}\Android\Android Studio"
)

$sdkFound = $false
$sdkPath = ""

foreach ($path in $androidStudioPaths) {
    if (Test-Path $path) {
        Write-Host "✅ Android Studio found at: $path" -ForegroundColor Green
        
        # Check for SDK in Android Studio installation
        $sdkPath = Join-Path $path "Sdk"
        if (Test-Path $sdkPath) {
            $sdkFound = $true
            break
        }
    }
}

# Check for standalone SDK
if (-not $sdkFound) {
    $standalonePaths = @(
        "${env:LOCALAPPDATA}\Android\Sdk",
        "C:\Android\Sdk"
    )
    
    foreach ($path in $standalonePaths) {
        if (Test-Path $path) {
            Write-Host "✅ Android SDK found at: $path" -ForegroundColor Green
            $sdkFound = $true
            $sdkPath = $path
            break
        }
    }
}

if ($sdkFound) {
    Write-Host "✅ Android SDK found!" -ForegroundColor Green
    
    # Update local.properties
    $localPropertiesPath = "local.properties"
    $content = Get-Content $localPropertiesPath -Raw
    
    # Replace the placeholder with actual path
    $content = $content -replace 'sdk\.dir=.*', "sdk.dir=$($sdkPath.Replace('\', '\\'))"
    
    Set-Content $localPropertiesPath $content
    Write-Host "✅ Updated local.properties with SDK path: $sdkPath" -ForegroundColor Green
    
    # Set ANDROID_HOME environment variable
    [Environment]::SetEnvironmentVariable("ANDROID_HOME", $sdkPath, "User")
    Write-Host "✅ Set ANDROID_HOME environment variable" -ForegroundColor Green
    
    Write-Host "`n🎉 Setup complete! You can now run:" -ForegroundColor Green
    Write-Host "   ./gradlew build" -ForegroundColor Yellow
    Write-Host "   ./gradlew data:test" -ForegroundColor Yellow
    
} else {
    Write-Host "❌ Android SDK not found!" -ForegroundColor Red
    Write-Host "`n📋 Please install Android SDK:" -ForegroundColor Yellow
    Write-Host "1. Download Android Studio from: https://developer.android.com/studio" -ForegroundColor White
    Write-Host "2. Install with default settings" -ForegroundColor White
    Write-Host "3. Run this script again" -ForegroundColor White
    Write-Host "`nOr install command line tools only:" -ForegroundColor Yellow
    Write-Host "1. Download from: https://developer.android.com/studio#command-tools" -ForegroundColor White
    Write-Host "2. Extract to C:\Android\Sdk" -ForegroundColor White
    Write-Host "3. Run this script again" -ForegroundColor White
}

Write-Host "`nPress any key to continue..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
