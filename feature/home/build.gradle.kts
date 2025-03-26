plugins {
    id("architectcoders.android.feature")
}

android {
    namespace = "ing.espinoza.architectcoders.ui.home"
}

dependencies {
    implementation(project(":domain:movie"))
}