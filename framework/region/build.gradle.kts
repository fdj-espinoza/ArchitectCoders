plugins {
    id("architectcoders.android.library")
    id("architectcoders.di.library")
}

android {
    namespace = "ing.espinoza.architectcoders.framework.region"
}

dependencies {
    implementation(project(":domain:region"))
    implementation(libs.play.services.location)
}