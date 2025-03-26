plugins {
    id("architectcoders.android.feature")
    id("architectcoders.di.library.compose")
}

android {
    namespace = "ing.espinoza.architectcoders.ui.home"
}

dependencies {
    implementation(project(":domain:movie"))
}