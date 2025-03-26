plugins {
    id("architectcoders.android.feature")
    id("architectcoders.di.library.compose")
}

android {
    namespace = "ing.espinoza.architectcoders.ui.detail"
}

dependencies {
    implementation(project(":domain:movie"))
}