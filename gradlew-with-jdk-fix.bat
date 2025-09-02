@echo off
REM Gradle wrapper with JDK image transformation fix
REM Usage: gradlew-with-jdk-fix.bat [gradle-command]

set GRADLE_OPTS=-Dandroid.enableJdkImageTransform=false

if "%1"=="" (
    echo Running Gradle with JDK image transformation fix...
    gradlew.bat
) else (
    echo Running: gradlew.bat %*
    gradlew.bat %*
)
