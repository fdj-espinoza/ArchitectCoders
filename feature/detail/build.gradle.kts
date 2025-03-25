plugins {
    id("architectcoders.android.feature")
}

android {
    namespace = "ing.espinoza.architectcoders.ui.detail"
}

dependencies {
    implementation(project(":domain:movie"))
}