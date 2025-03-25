plugins {
    id("architectcoders.android.library.compose")
}

android {
    namespace = "ing.espinoza.architectcoders.ui.common"
}

dependencies {
    implementation(libs.androidx.activity.compose)
}
