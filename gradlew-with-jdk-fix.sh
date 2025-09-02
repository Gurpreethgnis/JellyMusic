#!/bin/bash
# Gradle wrapper with JDK image transformation fix
# Usage: ./gradlew-with-jdk-fix.sh [gradle-command]

export GRADLE_OPTS="-Dandroid.enableJdkImageTransform=false"

if [ $# -eq 0 ]; then
    echo "Running Gradle with JDK image transformation fix..."
    ./gradlew
else
    echo "Running: ./gradlew $@"
    ./gradlew "$@"
fi
