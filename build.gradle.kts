// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
}

// Disable JDK image transformation globally
allprojects {
    afterEvaluate {
        tasks.matching { task ->
            task.name.contains("JdkImageTransform") || 
            task.name.contains("androidJdkImage")
        }.configureEach {
            enabled = false
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
