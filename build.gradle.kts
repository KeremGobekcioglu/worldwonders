// Top-level build file.
//
// The buildscript block overrides the Kotlin Gradle Plugin and KSP versions that
// AGP 9 bundles by default (KGP 2.2.10 / KSP 2.2.10-2.0.2). Without this, the
// build fails with "Class 'kotlin.Unit' was compiled with an incompatible
// version of Kotlin" because some dependencies ship Kotlin 2.4.0 metadata.
buildscript {
    dependencies {
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.ksp.gradlePlugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
}