// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    // https://medium.com/@rowaido.game/implementing-the-room-library-with-jetpack-compose-590d13101fa7
    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}