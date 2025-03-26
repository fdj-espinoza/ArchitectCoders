plugins {
    id("architectcoders.android.library")
    id("architectcoders.android.room")
    id("architectcoders.jvm.retrofit")
    id("architectcoders.di.library")
}

android {
    namespace = "ing.espinoza.architectcoders.framework.movie"
}

dependencies {
    implementation(project(":domain:movie"))
}